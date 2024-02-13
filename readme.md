# TEST APP

### Server start

1) Скопируйте репозиторий 
```bash
git clone https://github.com/vidroid16/gazprom-test-work.git
```
2) Задайте порты в файле /docker-start-confg.env

3) Запуск в неинтерактивном режиме как сервер
```bash
docker-compose  --env-file docker-start-confg.env up
```
Примечание: healthcheck бд не работает. Fatal error не получилось исправить

4) Api можно посмотреть в ru/gazprom/testwork/api/RandomUsersController.java
### Консольный старт
1) Запуск в интерактивном режиме 
```bash
docker-compose --env-file docker-start-confg.env run client-backend
```
2) Команды
```bash
upload 10 # загрузить 10 случайных пользователей в БД
get 5 filename.csv # загрузить 10 случайных пользователей из БД в файл. Файл очищается
```
3) Файлы хранятся в томе gazprom_serverfiles.
Можно в докер десктопе посмотреть

### Описание 
По структуре приложение типичный spring сервер.
Бизнес логика реализована в классе RandomUserService. В пакете utills логика парсинга сущностей
в CSV с помощью Apache СSV Utills. Консоль реализована в главном классе. Для маппинга сущностей
и дто используется ModelMapper.

### Как добавить выгрузку excel и другие форматы
Пример:
1) Имплементировать интерфейс 
```java
 public interface FormatCaster<T> {
    ByteArrayOutputStream cast(List<T> objects) throws IOException;
}
```
2) Переопределить метод cast и пометить как компонент 
```java
@Component
public class ExcelFormatCaster implements FormatCaster<UserDto>{
    @Override
    public ByteArrayOutputStream cast(List<UserDto> users) {
        return null; //Наша логика
    }
}
```
3) Добавить наш "кастер" в конфигурацию по имени
```java
@Component
@Configuration
public class CastersConfig {
    @Bean
    public HashMap<String, FormatCaster> casterByFormat(){
        HashMap<String,FormatCaster> casters = new HashMap<>();
        casters.put("csv", new CsvFormatCaster());
        casters.put("excel", new ExcelFormatCaster()); //вот так
        return casters;
    }
}
```
4) Inject-им бин куда надо и получем кастер по имени. Полученный стрим сохраняем в нужный файл
```java
private final HashMap <String, FormatCaster> casters;
/* .... */
ByteArrayOutputStream os = casters.get("csv").cast(userDtos);
```

### Узкие места и что можно улучшить
1. Решить N+1 проблему добавив EntityGraph или написав nativeQuery
2. Улучшить обработку ошибок
3. Написать больше логов и сделать нормальный логгер
4. Исправить healthcheck БД
5. Облагородить консольный ввод-вывод