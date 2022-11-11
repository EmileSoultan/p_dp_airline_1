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
**JWT (JSON Web Token)** служит для безопасной передачи информации между двумя участниками (в нашем случае клиентом и сервером). Токен содержит:
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
- создать POST запрос на ```/api/auth/token```
- в качестве тела запроса выбрать raw -> JSON
- вписать username(email) и password в JSON и выполнить запрос
- Если аутентификация прошла успешно сервер вернет **JWT**:  
  ![](src/main/resources/git_postman_and_swagger_guide/git_tutor/jwt_postman_1.png)

Авторизация на сервере:
- Теперь, чтобы выполнить любой запрос к серверу нужно в **header** запроса создать ключ **Authorization** и в качестве значения написать ```Bearer ``` + полученный ранее токен (Bearer обязательно с пробелом):  
  ![](src/main/resources/git_postman_and_swagger_guide/git_tutor/jwt_postman_2.png)
- Если время валидности токена истекло повторить процесс аутентификации

      
## Swagger
Swagger - это фреймворк для спецификации RESTful API, который позволяет интерактивно просматривать спецификацию,
отправлять запросы и получать ответы через Swagger UI.

С помощью Swagger-Codegen возможно сгенерировать клиета или сервер по спецификации API Swagger.

Swagger использует декларативный подход. С помощь аннотаций необходимо разметить методы, параметры, DTO.

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


