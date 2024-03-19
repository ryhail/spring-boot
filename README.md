# REST application
## Приложение использует Shikimori API Для получения информации об аниме
**[Shikimori API](https://shikimori.one/api/doc/1.0)**<br>
## Sringboot + JPA + Hibernate
## DB - PostgreSQL
# anime <-> genre many to many
# anime <-> studio many to one, bidirectional
# Doc
## /ANIME
### GET /anime - все аниме в базе данных
### GET /anime/{id} - получение информации об аниме с определённым id
### POST /anime - добавление нового аниме в базу данных
### PUT /anime - обновление информации о конкретном аниме
### DELETE /anime/{id} - удаление аниме по id
### GET /anime?search - поиск аниме по названию, русское/английское по внутренней базе и в случае отсутствия по Shikimori
### GET /anime/shikimori?animeName - сохранение в базу аниме по названию с Shikimori

## /GENRE
### GET /genre - получить все жанры 
### GET /genre/{id} - получить жанр по id
### GET /genre/shikimori - получить все жанры с шикимори
### POST /genre - добавить жанр
### PUT /genre - обновить жанр
### DELETE /genre/{id} - удалить жанр по id

## /STUDIO
### GET /genre/{id} - получить студию по id
### POST /studio - добавить сутдию
### PUT /studio - обновить студию
### DELETE /studio/{id} - удалить студию по id

    
