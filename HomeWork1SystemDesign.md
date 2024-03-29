# Система для бронирования рабочих мест в Коворкингах

## Схема сервиса / Доменная модель

[https://app.diagrams.net/?title=OTUS_HW_1.drawio#Uhttps%3A%2F%2Fdrive.google.com%2Fuc%3Fid%3D1w6pepgVvXP924bnrMkpyQtF9q2bS3lyj%26export%3Ddownload](app.diagrams.net)

### ![image](https://raw.githubusercontent.com/local-cat/otus-ms-arch-2024-homework/main/OTUS_HW_1.drawio.png)

> *Картинка немного устаревшая*

## Пользовательские сценарии

### История: Поиск и бронирование рабочего места

Я как пользователь хочу выбрать город, и коворкинг для работы в одном месте (сервисе/агрегаторе), узнать по стоимости и свободных местах на интересующие меня даты, и если меня устроит забронировать.


**Сценарий 1:** авторизация в клиентском приложении.
Дан пользователь не аутентифицированный  в клиентском приложении
Когда покупатель вводит правильный логин и пароль от своего аккаунта
Тогда в клиентском приложении отображается, что пользователь авторизован.

**Сценарий 2:** поиск подходящего коворкинга.
Пользователь может быть авторизован так и нет.
Для поиска подходящего коворкинга, он может быть и не авторизован.
Пользователь видит все размеженые в серсисе коворкинги, также он может фильтровать их по городам.

При выборе конкретного коворкинга он может узнать на какие даты есть свободные места и стоимость за интересующее его место.

**Сценарий 3:**  бронирование места
Если пользователь не авторизирован, для бронирования места, ему необходимо будет авторизоваться.
Если пользователь авторизован, после выбора нужного ему места и выбора дат для бронирования , он нажимает кнопку забронировать.
В случае, если бронирование было произведено, он получает, номер Брони (можно также слать на почту, на диаграмме этого нет), если бронь не возможна (другой пользователь ранее отпраивил бронь, на пересекающиеся даты и место), то сервис отвечает, что бронирование не возможно, с указанием причины.


### История: Получение и просмотр уведомлений админами коворкинга

Сценарий 1: Админ коворкинга получает уведомление о поступлении новой брони
Админ получает сообщение о новой брони в указанные каналы связи
Может зайти в инстерфейс и там увидеть загрузку по дням/неделям.

Сценарий 2: Снятие брони пользователя/пользователей
При необходимости, админ может снимать брони пользователей, с указанием причины (форм мажор, отключение элекстричества в здании, ремонт, плановые или внеплановые работы).
Пользователь, чья бронь была затронута получает уведомление на указанные каналы связи.

> *Идея для развития:* добавить возможность админам блокировать места на определены даты


## Модель предметной области и системные операции

Пользователь аутентифицируется в клиентском приложении
Пользователь в клиентском приложении смотрит и выбирает подходящий ему коворкинг
Авторизированый пользователь в клиентском приложении  делает бронь
Авторизированый пользователь в клиентском приложении  может редактировать настройки своего профиля
Каталог коворкингов, отдает информацию о всех коворкингах в сервисе
Сервис бронирования отдает информацию о возможных бронированиях
Сервис бронирования создает бронь у себя
Сервис бронирования отдает номер брони
Сервис бронирования отдает отказ с указанием причины, почему бронирование не возможно
Сервис нотификаций формирует задачи для сервисов рассылок , обращаясь к сервиcу настроек пользователя или коворкинга
Сервисы рассылок, слушают очередь и при нахождении задач для себя производят отправку сообщения


**

## Microservices Canvas

> Клиентское приложение, это веб/дестктом/мобайл приложение или серсис, который работает с апи сервиса


**Имя: Api Gateway**  
Запросы и команды: проксирует все запросы к микросервисам
Зависимости: Identity MS - по биреру првоверят, автоизован пользователь или явялетсья ли пользовтаель тем , за кого себя выдает ( првоерка подписи)
Подписан на события:

**Имя: Identity MS**  
Запросы и команды: 
* api/im/auth - Авторизует пользователя, возвращает бирер тоекн
* api/im/profile - Возвращает информацию по пользователю и его ролям
* api/im/token - Рест для получения рефреш токена
* api/im/verify_user - првоеряет валиден ли бирер токен пользователя
Зависимости: 
Подписан на события:

**Имя: Profile Settings**  
Запросы и команды: 
* api/profile/settings - работа с настройками пользователя
* api/profile/open_settings - работа с чтением данных о настройках пользователя для сервисов
Зависимости: 
Подписан на события:

**Имя:Coworking Settings**  
Запросы и команды: 
* api/profile/settings - работа с настройками коворкинга
* api/profile/open_settings - работа с чтением данных о коворкигах  для сервисов
Зависимости: 
Подписан на события:

**Имя: Coworking Api**  
Запросы и команды: 
* api/view/list/{params} - получение списка коворкинкгов по зааданым парамтерам/фильтрам
* api/view/coworking/{id} - получение октрытых данных по коворкингу
Зависимости: 
Подписан на события:

**Имя: Coworking Booking MS**  
Запросы и команды: 
* api/booking/seats/{coworkingId} - получение данных по местам в ковркинге с их статусом
* api/booking/seats/{coworkingId}/period/{startDate}/{endDate} - получение данных по местам в ковркинге с их статусом за опрденные даты
* api/booking/booking/{seatId}/period/{startDate}/{endDate} - бронирование места на выбранные даты
* api/booking/unbooking/{seatId}/period/{startDate}/{endDate} -снятие брони на выбранные даты

Зависимости: Notifycation MS
Подписан на события:

**Имя: Coworking Booking MS**  
Запросы и команды: 
* api/booking/seats/{coworkingId} - получение данных по местам в ковркинге с их статусом
* api/booking/seats/{coworkingId}/period/{startDate}/{endDate} - получение данных по местам в коворкинге с их статусом за опрденные даты
* api/booking/booking/{seatId}/period/{startDate}/{endDate} - бронирование места на выбранные даты
* api/booking/unbooking/{seatId}/period/{startDate}/{endDate} -снятие брони на выбранные даты

Зависимости: Notifycation MS 
Подписан на события: 


**Имя: Notifycation MS**  
Запросы и команды:  
* /notify/questions/{coworkingId}/-  отправка вопроса или предложения в конкретный коворкинг / форма обратной связи
* /notify/booking/seat/{seatId}/period/{period} -  отправка информации об успешном бронировании админам коворкинга (можно также и пользователя оповещать опционально)

Зависимости:  
Подписан на события: 

**Имя: Семейство Sender MS**  
Запросы и команды:  
Зависимости:  
Подписан на события: слушают топик брокера 
