# Lab7 — краткий гайд

## Что нового после lab6

Раньше API только возвращал данные. Теперь можно создавать, изменять, удалять мероприятия и искать по названию.

| HTTP-метод | Действие | Успешный код |
| --- | --- | --- |
| GET | Получить данные | 200 |
| POST | Добавить мероприятие | 201 |
| PUT | Заменить название и место существующего мероприятия | 200 |
| DELETE | Удалить мероприятие | 204, без тела ответа |

404 — ID не найден. 400 — неправильные данные запроса.

## Откуда берутся данные метода

- `@PathVariable("id")`: число из пути `/api/events/4`.
- `@RequestParam(name = "title", required = false)`: строка из `?title=quiz`; параметр необязательный.
- `@RequestBody EventInfo input`: Spring преобразует JSON тела запроса в объект Java.

`@JsonCreator` над пустым конструктором явно указывает Jackson, как создавать объект из JSON. `@JsonProperty(access = READ_ONLY)` у ID разрешает выводить его в ответе, но игнорирует ID во входном JSON.

У EventInfo появился пустой конструктор и setters для title/location, чтобы Spring мог заполнить объект из JSON. Контроллер передаёт сервису только название и место: ID из запроса не используется.

## Как идёт запрос

POST → метод контроллера → проверка title/location → `eventService.addEvent(...)` → новый объект в списке → HTTP 201 и JSON.

Сервис по-прежнему передаётся в конструктор контроллера самим Spring (dependency injection). Контроллер занимается HTTP, сервис — списком.

`ResponseEntity` позволяет вернуть одновременно HTTP-код и тело ответа:

```java
return ResponseEntity.status(201).body(event);
return ResponseEntity.notFound().build();
return ResponseEntity.noContent().build();
```

`List.of(...)` заменён на изменяемый `ArrayList`. `add` добавляет элемент, `set(i, updated)` заменяет его, `remove(i)` удаляет. `nextId` начинается с 4 и увеличивается только после добавления. Поиск: `toLowerCase(Locale.ROOT)` приводит обе строки к нижнему регистру без зависимости от языка компьютера, `contains` проверяет вхождение подстроки.

`validInput` проверяет null и `isBlank()`. `&&` прекращает проверку после false: если поле null, его метод isBlank не вызывается.

## Запуск

```bash
cd /home/alexseyka/learn/web/lab7
./gradlew --console=plain bootRun
```

Запросы ниже выполняй во втором терминале. `-i` показывает HTTP-код и заголовки, `-X` выбирает метод, `-H` задаёт заголовок, `-d` отправляет JSON. Если сервер уже запущен, повторно запускать его не нужно.

## Пять скриншотов для отчёта

Выполняй после свежего запуска: первые три ID — 1, 2, 3; новый будет 4. Если уже добавлял записи, используй ID из ответа POST.

**1. Список, поиск и отсутствующий ID:**

```bash
curl -i http://localhost:8080/api/events
curl -i 'http://localhost:8080/api/events?title=QUIZ'
curl -i http://localhost:8080/api/events/999
```

Ожидаются 200, 200 с JavaScript Quiz и 404.

**2. Добавление — 201:**

```bash
curl -i -X POST http://localhost:8080/api/events \
  -H 'Content-Type: application/json' \
  -d '{"title":"Java Workshop","location":"Room 303"}'
```

**3. Изменение — 200, ID остаётся прежним:**

```bash
curl -i -X PUT http://localhost:8080/api/events/4 \
  -H 'Content-Type: application/json' \
  -d '{"title":"Spring Workshop","location":"Room 404"}'
curl -i http://localhost:8080/api/events/4
```

**4. Удаление — 204, затем получение — 404:**

```bash
curl -i -X DELETE http://localhost:8080/api/events/4
curl -i http://localhost:8080/api/events/4
```

**5. Ошибка — 400, затем новое добавление — ID 5:**

```bash
curl -i -X POST http://localhost:8080/api/events \
  -H 'Content-Type: application/json' \
  -d '{"title":"","location":"Room 303"}'
curl -i -X POST http://localhost:8080/api/events \
  -H 'Content-Type: application/json' \
  -d '{"title":"New Event","location":"Main Hall"}'
```

На скриншотах должны быть видны команды, HTTP-коды и ответы. Можно использовать Postman вместо curl.

## Что показать на защите

Продемонстрировать GET, POST, PUT, DELETE, поиск и 404. Показать три аннотации входных данных и вызов сервиса из контроллера. Объяснить, что при перезапуске изменения исчезают: базы данных нет. Для небольшого изменения можно заменить `contains` на `startsWith`: поиск будет проверять начало названия, а не любую его часть.
