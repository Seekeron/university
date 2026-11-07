# Lab6 — краткий гайд для защиты

## Как идёт запрос

Браузер → GET /api/events → RegistrationController.events() → EventService.getEvents() → список объектов → JSON в браузере.

`localhost` — твой компьютер, `8080` — порт сервера. GET — HTTP-запрос на получение данных. Endpoint — конкретный адрес API.

## Что делает каждый файл

- `EventRegistrationApplication.java`: метод `main` запускает Spring Boot и встроенный сервер.
- `RegistrationController.java`: принимает запросы по адресам `/api/...` и возвращает данные.
- `EventService.java`: хранит три мероприятия и возвращает их или приветствие.
- `EventInfo.java`: описывает одно мероприятие — `id`, `title`, `location`; getters позволяют читать поля.
- `application.properties`: настройки имени приложения, курса и семестра.
- `build.gradle`: версия Java, Spring Boot и зависимости проекта.

## Аннотации

| Запись | Что означает здесь |
|---|---|
| `@SpringBootApplication` | Включает настройку приложения и поиск компонентов в текущем пакете и подпакетах |
| `@RestController` | Класс обрабатывает HTTP-запросы и возвращает данные в теле ответа |
| `@RequestMapping("/api")` | Общий префикс адресов контроллера |
| `@GetMapping("/events")` | Вызывает этот метод для GET-запроса на `/api/events` |
| `@Service` | Spring создаёт объект сервиса и управляет им |
| `@Value("${app.course}")` | Spring берёт значение настройки `app.course` и записывает в поле |

Аннотации — метаданные над классами, методами или полями. Spring читает их при настройке приложения.

## Dependency injection — главный вопрос

```java
private final EventService eventService;

public RegistrationController(EventService eventService) {
    this.eventService = eventService;
}
```

Контроллеру нужен сервис. Spring создаёт объект EventService и передаёт его в конструктор контроллера. `this.eventService` — поле объекта, `eventService` справа — параметр конструктора. `final` запрещает потом присвоить полю другой сервис. Это и есть внедрение зависимости через конструктор. В контроллере нет `new EventService()`.

Так контроллер занимается HTTP, а сервис — данными. При тестировании можно передать другой сервис. Bean — объект, которым управляет Spring.

## Методы и коллекции

```java
@GetMapping("/hello")
public Map<String, String> hello() {
    return Map.of("message", eventService.getWelcomeMessage());
}
```

`Map<String, String>` — пары «ключ → значение», примерно как словарь Python. `Map.of` создаёт карту, в которой нельзя добавлять, удалять или заменять записи. Здесь результат — `{"message":"Welcome to the Event Registration API"}`.

`List<EventInfo>` — список объектов EventInfo. `List.of(...)` создаёт список, в котором нельзя добавлять, удалять или заменять элементы. `new EventInfo(...)` создаёт каждое мероприятие через конструктор.

Spring через JSON-конвертер Jackson превращает возвращённые объекты в JSON: Map — в объект `{...}`, List — в массив `[...]`. Для EventInfo данные берутся через getters. Вручную JSON-строки мы не собираем.

## Три адреса

- `/api/hello`: приветствие из сервиса.
- `/api/events`: три мероприятия из сервиса.
- `/api/info`: настройки из application.properties, полученные через `@Value`.

## Запуск и демонстрация

```bash
cd /home/alexseyka/learn/web/lab6
./gradlew --console=plain bootRun
```

Если приложение уже работает на 8080, второй экземпляр не запускай. Для остановки своего запуска нажми Ctrl+C.

1. Открой http://localhost:8080/api/events.
2. Покажи `events()` в контроллере и `getEvents()` в сервисе.
3. Покажи конструктор контроллера и объясни, что сервис передаёт Spring.
4. Открой `/api/info` и покажи соответствующие строки в application.properties.
5. Для небольшого изменения поменяй `app.semester=3` на `app.semester=4`, перезапусти приложение и обнови `/api/info`.

Здесь нет базы данных: мероприятия заданы в Java-коде и хранятся в памяти. Из lab5 не переносился ввод через Scanner — теперь программа получает HTTP-запросы. Spring Boot настраивает и запускает приложение со встроенным сервером; Gradle собирает проект и запускает задачу bootRun.
