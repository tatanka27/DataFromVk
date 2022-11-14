
# DataFromVk

Сервис для получения ФИО пользователя с сайта [VK](https://vk.com/), а также признака участника группы.


# Сервисный ключ доступа

Для использования приложения вам нужен VK_TOKEN. Чтобы получить токен, надо зарегистрироваться на https://dev.vk.com/.

Для выполнения тестов необходимо создать файл .env с параметром `vk-access-token` и значением VK_TOKEN.

# Запуск в minikube

Запустите кластер minikube:

```console
$ minikube start
```

Разверните приложение:

```console
$ kubectl apply -f kuber.yml
```

Прокиньте сетевой доступ к сервису внутри кластера:

```console
$ minikube service -n tatanka27 datafromvk
|-----------|------------|-------------|---------------------------|
| NAMESPACE |    NAME    | TARGET PORT |            URL            |
|-----------|------------|-------------|---------------------------|
| tatanka27 | datafromvk |        8080 | http://192.168.49.2:30007 |
|-----------|------------|-------------|---------------------------|
🏃  Starting tunnel for service datafromvk.
|-----------|------------|-------------|------------------------|
| NAMESPACE |    NAME    | TARGET PORT |          URL           |
|-----------|------------|-------------|------------------------|
| tatanka27 | datafromvk |             | http://127.0.0.1:53841 |
|-----------|------------|-------------|------------------------|
🎉  Opening service tatanka27/datafromvk in default browser...
❗  Because you are using a Docker driver on windows, the terminal needs to be open to run it.
```
(для windows терминал оставлять открытым)

Выполните запросы к сервису, используя порт, указанный в выводе (в примере выше `53841`).

# REST API

Опсиание API смотри в [openapi.yaml](openapi.yaml).

# Примеры запроса

##  Получение спецификации OpenAPI сервиса

Предварительно надо авторизироваться в сервисе с данными администратора (смотри запрос "Авторизация").

### Запрос

`GET /api/v3/api-docs`

```console
curl http://localhost:8080/api/v3/api-docs -H "Authorization: Bearer JWT_TOKEN"
```

### Ответ

В ответе приходит json, который можно использовать на сайте [swagger](https://editor.swagger.io/).


## Регистрация пользователя

### Запрос

`POST /user/registration`

```console
curl http://localhost:8080/user/registration -H "Content-Type: application/json" --data @data.json -v    
```

Пример `data.json`:

```json
{
    "username": "test@test.ru",
    "password": "123456"
}
```

### Ответ

```text
{
    HTTP/1.1 200
}
```


## Авторизация

### Запрос

`POST /login`

```console
curl http://localhost:8080/login -H "Content-Type: application/json" --data @data.json -v    
```

Пример `data.json`:

```json
{
    "username": "admin@admin.ru",
    "password": "123456"
}
```

### Ответ

```json
{
  "token":"eyJhbGci...7I57_rEB0Al8RP3ChQRzk_JFze8z_48vW0DJQcKPgm_3qSQ"
}
```

Этот токен используется дальше для отправки запросов - JWT_TOKEN.

## Получения ФИО пользователя с сайта vk.com, а также признака участника группы VK

### Запрос

`GET /user_vk`

```console
curl http://localhost:8080/user_vk -X GET -H "Authorization: Bearer JWT_TOKEN" -H "Content-Type: application/json" -H "vk_service_token: VK_TOKEN" --data @data.json -v    
```

Пример `data.json`:

```json
{
    "user_id": "1",
    "group_id": "1"
}
```

### Ответ

```json
{
    "last_name":"Durov",
    "first_name":"Pavel",
    "middle_name":null,
    "member":false
}
```


# Удаление кластера

В открытом окне терминала нажмите CTRL+C, чтобы оставноить проброс портов.

Чтобы удалить приложение из кластера:

```console
$ kubectl delete -f kuber.yml
```

Чтобы удалить кластер:

```console
$ minikube delete
```