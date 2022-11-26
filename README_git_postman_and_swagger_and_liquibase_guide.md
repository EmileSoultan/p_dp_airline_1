# Документация JMStack
## Работа c git
### Клонирование проекта

1. На странице репозитория убедитесь, что выбрана ветка **master** (1), нажмите кнопку **Clone** (2), скопируйте ссылку (3).

![](src/main/resources/git_postman_and_swagger_guide/git_tutor/git_clone_url.png)

2. Откройте **Intellij IDEA**, нажмите **Get from version control** на экране приветствия, либо **VCS | Git | Clone...** в меню.

![](src/main/resources/git_postman_and_swagger_guide/git_tutor/git_clone_get.png)

![](src/main/resources/git_postman_and_swagger_guide/git_tutor/git_clone_get_alt.png)

3. Вставьте скопированную ссылку в строку **URL**, нажмите **Clone**.

![](src/main/resources/git_postman_and_swagger_guide/git_tutor/git_clone_clone.png)

### Перед внесением изменений в код
Создайте новую ветку в git-репозитории и работайте в ней. Для этого:
1. Нажмите на текущую ветку **master** в правом нижнем углу.


![](src/main/resources/git_postman_and_swagger_guide/git_tutor/git_branch.png)

2. Выберите **New branch**.

![](src/main/resources/git_postman_and_swagger_guide/git_tutor/git_branch_create.png)

3. Введите название своей новой ветки (на ваше усмотрение) и нажмите **Create**.

![](src/main/resources/git_postman_and_swagger_guide/git_tutor/git_branch_name.png)

### Добавление своего кода в общий репозиторий. Git push.

Прежде чем создать merge request вам необходимо подготовить вашу ветку к отправке в общий репозиторий.

1. Нажмите на текущую ветку в правом нижнем углу. Выберите опцию **master | update**. 
Таким образом вы скачаете в свою локальную ветку **master** все коммиты которые были замержены, 
пока вы работали в своей ветке.

![](src/main/resources/git_postman_and_swagger_guide/git_tutor/git_premerge_update_master.png)

2. Убедитесь, что в данный момент активна ваша рабочая ветка (занчек ярлыка слева от имени, как у ветки my-branch на скриншоте).
Выберите опцию **master | Merge into Current**. Таким образом вы добавите все изменения из ветки **master** в вашу ветку. При возникновении конфликтов разрешите их.

![](src/main/resources/git_postman_and_swagger_guide/git_tutor/git_premerge_merge_master.png)

3. ---**ВАЖНО**--- Убедитесь что проект собирается и запускается.

4. Выберите вашу ветку и нажмите на **Push...**, чтобы добавить её в общий репозиторий.

![](src/main/resources/git_postman_and_swagger_guide/git_tutor/git_premerge_push.png)

### Создание merge request

1. Создайте новый merge request. В качестве **Source branch** выберите свою ветку, **Target branch** - **master**.

![](src/main/resources/git_postman_and_swagger_guide/git_tutor/git_merge_req.png)

![](src/main/resources/git_postman_and_swagger_guide/git_tutor/git_merge_req_new.png)

![](src/main/resources/git_postman_and_swagger_guide/git_tutor/git_merge_req_src_trg.png)

2. Проверьте данные, допишите комментарии при необходимости. Обратите внимание на опцию **Delete source branch when merge request is accepted**.
Завершите создание реквеста, приложите ссылку на него в карточку таска на Trello.

![](src/main/resources/git_postman_and_swagger_guide/git_tutor/git_merge_req_final.png)


## Аутентификация и авторизация с помощью JWT через Postman
**JWT (JSON Web Token)** служит для безопасной передачи информации между двумя участниками (в нашем случае клиентом и сервером) [подробнее от JWT тут](https://struchkov.dev/blog/what-is-jwt/). Токен содержит:
- логин(email) и роль пользователя
- дату создания
- дату, после которой токен не валиден

Данные токена подписаны **HMAC-256** с использованием секретного ключа, известного только серверу аутентификации и серверу приложений(в нашем случае это одно приложение) - т.о. сервер приложений, при получении токена, сможет проверить не менялись ли его данные с момента создания.  
![](src/main/resources/git_postman_and_swagger_guide/git_tutor/jwt_schema.png)
1) Пользователь посылает логин/пароль серверу аутентификации
2) Сервер аутентифицирует пользователя и возвращает ему **JWT**
3) К каждому запросу на сервер приложений пользователь прикрепляет **JWT**
4) На основе **JWT** сервер приложений авторизует пользователя и предоставляет доступ к ресурсу (или не предоставляет - если пользователь не прошел авторизацию или время валидности токена истекло)

Аутентификация на сервере:
- зайти в **Postman** 
- создать POST запрос на ```/api/auth/login```
- в качестве тела запроса выбрать raw -> JSON
- вписать username(email) и password в JSON и выполнить запрос
- Если аутентификация прошла успешно сервер вернет **JWT** в виде ```accessToken``` и ```refreshToken```. 
  ![](src/main/resources/git_postman_and_swagger_guide/git_tutor/jwt_postman_1.png)

Авторизация на сервере:
- Теперь, чтобы выполнить любой запрос к серверу нужно в **header** запроса создать ключ **Authorization** и в качестве значения написать ```Bearer ``` + полученный ранее токен (Bearer обязательно с пробелом)
или зайти во вкладку ```Authorization```, в выпадающем списке блока ```Type``` выбрать ```Bearer Token```, в ```Token``` вставить полученный ```accessToken``` 
  ![](src/main/resources/git_postman_and_swagger_guide/git_tutor/jwt_postman_2.png)
- Если время валидности токена истекло, тогда сделать POST запрос на ```/api/auth/token```
- вписать ```refreshToken``` и выполнить запрос
- сервер вернет ```accessToken```
- Чтобы получить новый ```accessToken``` и ```refreshToken``` необходимо сделать POST запрос на ```/api/auth/refresh```
- Если время валидности ```refreshToken``` токена истекло повторить процесс аутентификации

Подробная инструкция о реализации [JWT тут](https://struchkov.dev/blog/jwt-implementation-in-spring/).
      
## Swagger
Swagger - это фреймворк для спецификации RESTful API, который позволяет интерактивно просматривать спецификацию,
отправлять запросы и получать ответы через Swagger UI.

С помощью Swagger-Codegen возможно сгенерировать клиента или сервер по спецификации API Swagger.

Swagger использует декларативный подход. С помощью аннотаций необходимо разметить методы, параметры, DTO.

**Swagger Tools**

Swagger или OpenAPI framework состоит из 4 основных компонентов:

- Swagger Core - позволяет генерировать документацию на основе существующего кода основываясь на Java Annotation.

- Swagger Codegen - позволит генерировать клиентов для существующей документации.

- Swagger UI - красивый интерфейс, который представляет документацию. Дает возможность просмотреть какие типы запросов есть, описание моделей и их типов данных.

- Swagger Editor - Позволяет писать документацию в YAML или JSON формата.

Чтобы войти в Swagger UI - необходимо, после запуска приложения в браузере,  ввести один из следующих адресов:
- http://localhost:8080/swagger-ui/     
- http://localhost:8080/swagger-ui/index.html

![](src/main/resources/git_postman_and_swagger_guide/git_tutor/Example.png)

**JWT авторизация**

Чтобы оправлять запросы через Swagger UI были авторизированы - необходимо выполнить следующие действия:
1. Получить JWT токен:

    a) Через Postman

    b) Через Swagger-UI:

            -выбрать поле Authentication

            -Authenticate user
            
            -нажать кнопку "Try it out"

            -ввести имя пользователя и пароль

            -нажать Execute

            -скопировать токен из Response body
![](src/main/resources/git_postman_and_swagger_guide/git_tutor/Authentication.png)
2. Нажать на кнопку **Authorize** и в появившемся окне в поле Value ввести токен в виде
"Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzd........."
![](src/main/resources/git_postman_and_swagger_guide/git_tutor/Authentication1.png)

3. Нажать **Authorize**, после чего можно отправлять остальные запросы из Swagger UI в случае необходимости.


**Основные аннотации используемые для документирования кода:**

- @Operation - Описывает операцию или обычно метод HTTP для определенного пути.
- @Parameter - Представляет один параметр в операции OpenAPI.
- @RequestBody - Представляет тело запроса в операции
- @ApiResponse - Представляет ответ в операции
- @Tag - Представляет теги для операции или определения OpenAPI.
- @Server - Представляет серверы для операции или для определения OpenAPI.
- @Callback - Описывает набор запросов
- @Link - Представляет возможную ссылку времени разработки для ответа.
- @Schema - Позволяет определять входные и выходные данные.
- @ArraySchema - Позволяет определять входные и выходные данные для типов массивов.
- @Content - Предоставляет схему и примеры для определенного типа мультимедиа.
- @Hidden - Скрывает ресурс, операцию или свойство
- @ApiParam - Предназначена для параметров запроса ресурсов API

Примеры использования:

````
@Tag(name = "User", description = "The User API")
@RestController
public class UserController {}
````
```
@Operation(summary = "Gets all users", tags = "user")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Found the users",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = UserApi.class)))
                    })
    })
    @GetMapping("/users")
    public List<UserApi> getUsers()

```
```
@Operation(summary = "Get list of answers by question id", responses = {
            @ApiResponse(description = "Got list of answers", responseCode = "200",
                        content = @Content(array = @ArraySchema(schema = @Schema(implementation = AnswerDto.class)))),
            @ApiResponse(description = "No answers with such question id - return empty list", responseCode = "200"),
            @ApiResponse(description = "Wrong type of question id", responseCode = "400")
    })
    @GetMapping
    public ResponseEntity<?> getAnswerByQuestionId(@PathVariable Long questionId) {
        return  ResponseEntity.ok(answerDtoService.getAnswerById(questionId));
    }
```

Для получения более детальной информации воспользуйтесь одной из ссылок:

- https://habr.com/ru/post/541592/
- https://habr.com/ru/post/536388/
- https://swagger.io/docs/specification/about/


## Liquibase

**Основные понятия:**

Пишу от себя из головы, поэтому за неточности в названиях прошу не пинать.

Можно сказать, что Liquibase это тот же git, только для баз данных.
Основная его задача - это контроль за миграцией схемы
(не путать с миграцией чего-либо на Земле и это не связанно с переносом данных)
,т.е. управлением и отслеживанием состояния структур таблиц. В общем всё что связанно с
DDL - это про Liquibase. При помощи него Вы создаёте и изменяете таблицы и их
свойства, а Liquibase отслеживает каждое изменение и позволяет откатываться
к предыдущим состояниям.

changeSet - набор изменений, можно сказать это точка commit-а.

databasechangelog - основная таблица в которой прописаны все произошедшие 
изменения с помощью скриптов. Создаётся самим liquibase-ом в рабочей схеме
базы данных.

databasechangeloglock - тоже создаётся liquibase-ом, можно забыть про неё, 
нужна для отслеживания одновременного обращения к таблицам БД. Её использует 
liquibase, записывает туда данные во время обновлений и если произойдёт 
такая маловероятная ситуация, что два пользователя захотят обновить таблицы 
в одно и тоже время, то одному из них liquibase просто откажет. 

**Принцип действия и взаимодействия**

Главное правило, существующие файлы не править, только добавлять новые, 
liquibase сохраняет их контрольные суммы в databasechangelog, и если они 
не сойдутся, то программа не запустится.

В application.yml указана начальная точка для liquibase - это  db.changelog-master.xml.
Это главный файл где указаны основные changeSet-ы. Все файлы лежат в директории 
db.changelog

----
    <include file="новая директория/имя файла.xml" relativeToChangelogFile="true"/>

    <changeSet id="уникальный id (пример 1.1)" author="Ваше имя">
        <tagDatabase tag="Уникальный тег (пример v-1.1)"/>
    </changeSet>
----

Все новые изменения создавайте в новой директории и называйте её новой версией,
к примеру v-1.1 В этой диретории создайте файл .xml с номером версии
(пример db.changelog-v-1.1.xml) и укажите путь к нему в db.changelog-master.xml 
как показано выше. Уникальность id необходима для идентификации в таблице
databasechangelog, а уникальность тегов для возможности отката к предыдущей 
версии. 

LiquiBase не даёт ветвления у него строго поступательные коммиты(changeSet-ы), 
по которым в случае чего можно откатиться назад.

В этом файле Вы уже создаёте такие же ссылки и changeset-ы, но уже на файлы с 
sql-скриптами. эти файлы Вы помещаете в эту же директорию.
----
    <include file="имя первого файла (пример 01-create-table-searches.sql)" 
            relativeToChangelogFile="true"/>

    <changeSet id="уникальный id подверсии (пример 1.1.1)" author="Ваше имя">
        <tagDatabase tag="уникальный тег (пример v-1.1-create-table-searches)"/>
    </changeSet>

    <include file="имя второго файла (пример 02-create-table-search-results.sql" 
            relativeToChangelogFile="true"/>

    <changeSet id="уникальный id подверсии" author="Ваше имя">
        <tagDatabase tag="имя второго файла (пример v-1.1-create-table-search-results)"/>
    </changeSet>
----

На каждое действие старайтесь делать один файл с маленьким sql-скриптом.

----
CREATE TABLE searches

(

id                   BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,

from_id              BIGINT,

to_id                BIGINT,

return_date          date,

departure_date       date NOT NULL,

number_of_passengers INTEGER NOT NULL,

CONSTRAINT "searchesPK" PRIMARY KEY (id)

);

ALTER TABLE searches

ADD CONSTRAINT "FK1es2ks4yv25n78jlh6vfeiadc" FOREIGN KEY (from_id) REFERENCES destination (id);

ALTER TABLE searches

ADD CONSTRAINT "FKmr4214480arwkx4h595d1bia6" FOREIGN KEY (to_id) REFERENCES destination (id);

----

LiquiBase каждый раз начинает с начала файла мастер, там где уже есть запись в таблице
databasechangelog он только сверяет контрольные суммы, ничего не должно быть изменено.
Когда находит новые скрипты, то выполняет их и записывает в таблицу.

Если необходимо запустить заново все скрипты, то удалите обн таблицы databasechangelog и
databasechangeloglock и запустите программу.

Если нужен откат, то использовать его можно через maven plugins 
liquibase:rollback (к примеру -D liquibase.rollbackTag=v-1.0), 
но для этого в ChangeSet-ах надо прописать тег rollback, чтобы он понимал
как откатывать назад.

Пример:

----

      <changeSet информация по changeSet-у>
         ... информация по changeSet-у

         <rollback>
            <delete tableName="hero">
               <where>name = 'Савельич'</where>
            </delete>
            <delete tableName="book">
               <where>title = 'Капитанская дочка'</where>
            </delete>
            <delete tableName="hero">
               <where>first_name = 'Александр'</where>
            </delete>
         </rollback>
      </changeset>

----

Если дописали в код новые сущности или изменили отношения или constraint-ы, 
то можете сделать maven -> clean -> package, потом в его плагинах найдите 
liquibase:diff и запустите. Будет сгенерирован файл в директорию autogenerated.
Но не было времени доработать нормальную зависимость плагина с hibernate, 
поэтому там будут лишние sql скрипты, их видно, liquibase просит там, к примеру, 
удалить constraint, а потом его такой же добавить... В общем пустые действия, но 
при изменении сущностей, если нет желания париться с SQL, то тут найдите свой код 
и скопируйте.

