SpeleoTools

На текуший момент есть некая реализация CRUD для одной сущности. Наследуемся от андроидовского абстрактного SQLiteOpenHelper и пишем методы для работы с таблицей.

Моели "карта" и "пикет" созданы, SQLiteХэлепер для карт тоже созданы. Не хочу повторять код хэлпера для пикетов. Думаю над более красивым решением для базы. (на крайняк можно посмотреть и попробовать внедрить какую-нить ОРМ типа greenDAO).

Нашёл инетресные вещи ContentProvider и Loader... возможно не придётся реализовывать хэлперы.