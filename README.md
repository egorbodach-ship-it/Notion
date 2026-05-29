# SimpleEssentials (Minecraft 1.20.1)

Лёгкий плагин для Spigot/Paper 1.20.1.

## Команды

| Команда | Действие | Право (permission) |
|---------|----------|--------------------|
| `/gm0 [ник]` | Режим Выживание (Survival) | `simpleessentials.gamemode` |
| `/gm1 [ник]` | Режим Творческий (Creative) | `simpleessentials.gamemode` |
| `/gm2 [ник]` | Режим Приключение (Adventure) | `simpleessentials.gamemode` |
| `/gm3 [ник]` | Режим Наблюдатель (Spectator) | `simpleessentials.gamemode` |
| `/feed [ник]` | Восстановить голод/насыщение | `simpleessentials.feed` |
| `/heal [ник]` | Восстановить здоровье, потушить огонь | `simpleessentials.heal` |

- Без аргумента команда действует на самого игрока.
- С ником `[ник]` — на указанного онлайн-игрока (можно из консоли).
- По умолчанию права выдаются операторам сервера (OP).

## Как собрать .jar

Нужны JDK 17+ и Maven (или просто открыть проект в IntelliJ IDEA).

### Вариант 1: Maven из терминала
```bash
cd SimpleEssentials
mvn clean package
```
Готовый файл появится тут: `target/SimpleEssentials-1.0.0.jar`

### Вариант 2: IntelliJ IDEA
1. File -> Open -> выбрать папку `SimpleEssentials`.
2. Дождаться, пока подтянутся зависимости (нужен интернет — скачается Spigot API 1.20.1).
3. Справа на панели Maven: Lifecycle -> package (двойной клик).
4. Забрать `.jar` из папки `target`.

## Установка на сервер
1. Скопировать `SimpleEssentials-1.0.0.jar` в папку `plugins` сервера.
2. Перезапустить сервер (или `/reload confirm`).

Сервер должен работать на Java 17+ (для 1.20.1 это обязательно).
