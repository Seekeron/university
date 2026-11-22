# Lab8 — короткий гайд

Основа взята из lab7. Контроллер и адреса почти те же, Swagger сохранён. Главное изменение — события хранятся в PostgreSQL. После перезапуска приложения они остаются в базе.

## Как проходит запрос

`HTTP → RegistrationController → EventService → EventRepository → PostgreSQL`

Ответ возвращается обратно по этой цепочке и превращается в JSON.

## Что добавилось

`Event` — Java-класс, объект которого соответствует строке таблицы. `@Entity` сообщает об этом JPA, `@Table(name = "events")` задаёт имя таблицы. `@Id` отмечает первичный ключ. `@GeneratedValue(strategy = GenerationType.IDENTITY)` поручает базе генерировать ID. Тип `Long` позволяет хранить большие целые числа и значение null до сохранения нового объекта. Пустой конструктор нужен для создания объектов библиотеками.

`EventRepository` — интерфейс `extends JpaRepository<Event, Long>`. Здесь Event — тип объекта, Long — тип его ID. Spring создаёт реализацию интерфейса. Готовые методы: `findAll()`, `findById(id)`, `save(event)`, `delete(event)`.

`findByTitleContainingIgnoreCase(title)` — поиск по части названия без учёта регистра. Spring строит запрос по названию метода.

`EventService(EventRepository eventRepository)` получает репозиторий через конструктор. ArrayList и nextId удалены. `findById(id).orElse(null)` возвращает найденный объект или null, если его нет.

При POST создаём новый Event без ID, затем вызываем save. При PUT сначала ищем существующий Event, меняем поля и сохраняем; ID сохраняется. При DELETE ищем объект и удаляем. `@Transactional` объединяет чтение и изменение в одну транзакцию: изменения подтверждаются вместе при успешном завершении метода.

`@JsonProperty(access = READ_ONLY)` позволяет отправлять ID в ответе, а входящий ID игнорируется. Реальный ID создаёт PostgreSQL.

Настройки подключения берутся из DB_USER, DB_PASSWORD и необязательного DB_URL. `ddl-auto=update` создаёт или обновляет таблицу при запуске.

## Проверка в Swagger и скриншоты

1. Запусти проект по README и открой http://localhost:8080/swagger-ui.html.
2. POST /api/events → Try it out. Введи `{"title":"Java Workshop","location":"Room 303"}`. Execute → 201. Запомни ID. Скриншот 1: запрос и ответ.
3. PUT /api/events/{id}: введи этот ID и `{"title":"Spring Quiz","location":"Room 404"}`. Получишь 200 с прежним ID. GET /api/events?title=QUIZ должен найти запись.
4. В psql или pgAdmin выполни `SELECT id, title, location FROM events ORDER BY id;`. Скриншот 2: строка из базы.
5. Останови приложение Ctrl+C и запусти снова. GET по тому же ID → 200, прежние данные. Скриншот 3: результат после перезапуска. При возможности перезапусти и PostgreSQL, затем повтори GET.
6. POST с `{"title":"","location":"Room 303"}` → 400. Скриншот 4: проверка ошибки.
7. DELETE по сохранённому ID → 204, повторный GET → 404. Повтори SQL-запрос: удалённой строки нет. GET/PUT/DELETE для отсутствующего ID с корректным телом PUT → 404.

Пароли не должны попадать в скриншоты. После проверки можно добавить свои четыре скриншота в отчёт.

## Основа отчёта

Name: Alexey V. Group: It302502 DS.

Purpose: Extend the Lab 7 REST API with Spring Data JPA and PostgreSQL, preserve CRUD and title search, and verify persistent storage.

Вставь четыре скриншота из шагов выше с подписями.

Conclusion: The API stores events in PostgreSQL using Spring Data JPA. The Event entity maps Java objects to the events table, and the database generates primary keys. The repository provides CRUD operations and case-insensitive title search. Validation rejects blank input, and stored records remain available after restarting the application.
