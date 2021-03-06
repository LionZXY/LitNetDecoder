# LitNetDecryptor
## Что это?
Это утилита на Kotlin для дешифровки книг из приложения [LitNet](https://play.google.com/store/apps/details?id=com.litnet).

ВНИМАНИЕ: Для использования необходимы root-права на смартфоне

## Как этим пользоваться?
- Авторизуйтесь в приложении LitNet. Прокликайте те книги в библиотеке, что хотели бы получить. Подождите минуты 2-3
- Убедитесь что на вашем телефоне есть root права и используйте файловый менеджер, который поддерживает их (Total Commander, ES File Manager)
- Скачайте последний релиз [LitNetDecoder.jar](https://github.com/LionZXY/LitNetDecoder/releases/)
- Скопируйте `/data/data/com.litnet/databases/library.db` из Android в папку `in` рядом с jar
- Скопируйте папку `/data/data/com.litnet/files/chapters` из Android в папку `in` рядом с jar (в результате должна появиться папка `in/charapters`)
```
├── in
│   ├── chapters
│   │   ├── 521.gz
│   │   └── 523.gz
│   └── library.db
└── LitNetDecryptor.jar
```

- Запустите в папке `java -jar LitNetDecryptor.jar`
- Не все читалки поддерживают устаревший Epub2, поэтому советую прогнать файлы через [конвертер](https://ebook.online-convert.com/ru/convert-to-epub)

## Решения проблем:
**Q: Error: Unable to access jarfile <path>**
  
A: Попробуйте переложить все в папку без русских букв и без пробелов


**Q: Caused by: java.security.InvalidKeyException: Illegal key size or default parameters**

A: Нужно пропатчить JRE для поддержки AES 256. [Клац](https://stackoverflow.com/questions/6481627/java-security-illegal-key-size-or-default-parameters) и [клац](https://eax.me/java-crypto-workaround/)
  

## Что в результате?
В папке `bookOut` будут все ваши книги из библиотеки. Если что-то не получиться скачать, в консоль выведится:
```
Не найдена часть %Название главы% для книги %Название книги%
```
Если вам важны эти главы, прокликайте их вручную в приложении и повторите шаги

У каждой книги будет обложка (если она есть), аннотация, разделение по главам и все такое. 

## Как это работает?
Приложение сохраняет локально книги, чтобы можно было читать в оффлайне. Мы достаем эти книги и сохраняем как epub

## Создателям LitNet и правообладателям
Если вдруг эта программа вам чем-то не нравится, я тут же удалю её по первой просьбе. Пишите t.me/lionzxy или vk.com/lionzxy
