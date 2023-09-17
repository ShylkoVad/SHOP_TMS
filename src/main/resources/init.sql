--------------------------------------------------------
--  DDL for schema shop
--------------------------------------------------------
DROP SCHEMA IF EXISTS shop;
CREATE SCHEMA IF NOT EXISTS shop;
--------------------------------------------------------
--  Table shop.users
--------------------------------------------------------
DROP TABLE IF EXISTS shop.users;
CREATE TABLE IF NOT EXISTS shop.users (
    id       INT            NOT NULL AUTO_INCREMENT,
    name     VARCHAR(45)    NOT NULL,
    surname  VARCHAR(60)    NOT NULL,
    birthday Timestamp      NOT NULL,
    balance  DECIMAL(10, 2) NOT NULL,
    email    VARCHAR(45)    NOT NULL,
    password VARCHAR(45)    NOT NULL,
    street   VARCHAR(45),
    accommodation_number VARCHAR(45),
    flat_number VARCHAR(45),
    phone_number VARCHAR(45),
    PRIMARY KEY (id),
    UNIQUE INDEX IDX_USERS_USER_ID_UNIQUE (id ASC),
    UNIQUE INDEX IDX_USERS_EMAIL_UNIQUE (email ASC),
    UNIQUE INDEX IDX_USERS_PASSWORD_UNIQUE (password ASC)
    );

INSERT INTO shop.users(name, surname, birthday, balance, email, password, street, accommodation_number, flat_number, phone_number)
VALUES ('Вадим', 'Шилько', '1984-09-08', 150.00, 'shilko_vad@mail.ru', '1234', 'ул. Маяковского', '8', '167', '+375297191205');
--------------------------------------------------------
--  Table shop.categories
--------------------------------------------------------
DROP TABLE IF EXISTS shop.categories;
CREATE TABLE IF NOT EXISTS shop.categories (
    id   INT         NOT NULL AUTO_INCREMENT,
    name VARCHAR(45) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE INDEX IDX_CATEGORIES_CATEGORY_ID_UNIQUE (id ASC),
    UNIQUE INDEX IDX_CATEGORIES_NAME_UNIQUE (name ASC)
    );

INSERT INTO shop.categories(name) VALUES('Платы Arduino');
INSERT INTO shop.categories(name) VALUES('Наборы Arduino');
INSERT INTO shop.categories(name) VALUES('Датчики Arduino');
INSERT INTO shop.categories(name) VALUES('Дисплеи и индикаторы');
INSERT INTO shop.categories(name) VALUES('Инструмент');
INSERT INTO shop.categories(name) VALUES('Аксессуары для умного дома');
INSERT INTO shop.categories(name) VALUES('Литература по программированию');
--------------------------------------------------------
--  Table shop.products
--------------------------------------------------------
DROP TABLE IF EXISTS shop.products;
CREATE TABLE IF NOT EXISTS shop.products (
    id          INT          NOT NULL AUTO_INCREMENT,
    name        VARCHAR(90)  NOT NULL,
    description VARCHAR(3000) NOT NULL,
    price       DOUBLE       NOT NULL,
    category_id  INT          NOT NULL,
    PRIMARY KEY (id),
    UNIQUE INDEX IDX_PRODUCTS_ID_UNIQUE (id ASC),
    CONSTRAINT FK_PRODUCTS_CATEGORY_ID_CATEGORIES_ID
    FOREIGN KEY (category_id)
    REFERENCES shop.categories (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
    );

INSERT INTO shop.products(name, description, price, category_id)
VALUES ('Uno R3',
        'Контроллер Uno является самым подходящим вариантом для начала работы с платформой: она имеет удобный размер (не слишком большой, как у Mega и не такой маленький, как у Nano), достаточно доступна из-за массового выпуска всевозможных клонов, под нее написано огромное количество бесплатных уроков и скетчей.',
        42.00, 1);
INSERT INTO shop.products(name, description, price, category_id)
VALUES('Mega 2560',
       'Плата Arduino Mega 2560 предназначена для создания проектов, в которых не хватает возможностей обычных Arduino Uno. В этом устройстве максимальное из всех плат семейства Arduino количество пинов и расширенный набор интерфейсов.',
       55.20, 1);


INSERT INTO shop.products(name, description, price, category_id)
VALUES('Scratch+Arduino',
       'Набор подготовлен по материалам популярной книги Дениса Голикова «Scratch и Arduino. 18 игровых проектов для юных программистов микроконтроллеров».',
       350, 2);
INSERT INTO shop.products(name, description, price, category_id)
VALUES('Базовый набор "Arduino" 2.0',
       'Если вы хотите не только изучить основы использования популярной микроконтроллерной платформы Arduino для разработки электронных проектов.',
       340, 2);


INSERT INTO shop.products(name, description, price, category_id)
VALUES('Датчик контроля качества воздуха',
       'Модуль Grove - Air quality sensor v1.3 представляет собой датчик, предназначенный для контроля качества воздуха в помещении.',
       23, 3);
INSERT INTO shop.products(name, description, price, category_id)
VALUES('Датчик электричества на основе TA12-200',
       'Модуль Grove - Electricity Sensor представляет собой датчика электричества на основе трансформатора тока ТА12-200, который может преобразовывать большой переменный ток в малую амплитуду.',
       18, 3);


INSERT INTO shop.products(name, description, price, category_id)
VALUES('Джереми Блум. Изучаем Arduino',
       'Учебник переведен на русский и содержит подробные уроки для программирования микроконтроллера от известного автора. К плюсам данной книги можно отнести ссылки на информационный сайт, а также наличие видео уроков от Джереми Блума на YouTube (они тоже переведены на русский язык и озвучены).',
       36, 7);
INSERT INTO shop.products(name, description, price, category_id)
VALUES('Проекты с использованием Arduino',
       'Учебник с наиболее глубоким изучением языка программирования Arduino, каждая команда разобрана автором в отдельном разделе с примером скетча. В учебнике есть раздел с обзором различных плат Arduino и подробно рассмотрено подключение радио модулей для создания проектов на дистанционном управлении.',
       42, 7);


INSERT INTO shop.products(name, description, price, category_id)
VALUES('ZD-10D (12-0251), Держатель плат "третья рука" с лупой 3D',
       'ZD-10R Держатель "третья рука" с лупой х2.5 служит главным образом при проведении паяльно-сборочных работ в электронике, ювелирном деле, сборке и ремонте для зажима мелких деталей в любом нужном положении, когда не хватает двух рук.',
       24, 5);
INSERT INTO shop.products(name, description, price, category_id)
VALUES('YIHUA 908+, Паяльник с регулировкой температуры',
       'Описание: Рабочее напряжение 220В 50Гц; Мощность паяльника 65Вт; Температурный диапазон 200-480°',
       155, 5);


INSERT INTO shop.products(name, description, price, category_id)
VALUES('Grove - 4-Digit Display, 4-х разрядный модуль дисплея',
       'Цифровой дисплейный модуль с Grove интерфейсом построенный на базе 4-х разрядного семисегментного индикатора и управляющего драйвера TM1637. Он прекрасно подходит для проектов требующих алфавитно-цифровой дисплей.',
       52, 4);
INSERT INTO shop.products(name, description, price, category_id)
VALUES('Character LCD 2x16 with blue backlight',
       'Матричный, ЖКИ с подсветкой для наборов фирмы MIKROELEKTRONIKA, формат 2Х16.',
       155, 4);


INSERT INTO shop.products(name, description, price, category_id)
VALUES('Aqara Smart Door Lock N100 (Zigbee), Умный дверной замок',
       'Умный дверной замок Aqara N100 поможет сделать Ваш дом более безопасным, а также позволит отказаться от использования ключей.',
       1170, 6);
INSERT INTO shop.products(name, description, price, category_id)
VALUES('Центр управления умным домом Hub E1 HE1-G01',
       'Центр управления умным домом AQARA Hub E1 HE1-G01 - самый компактный, но не уступающий по возможностям другим центр умного дома Aqara.',
       120, 6);


--------------------------------------------------------
--  Table shop.orders
--------------------------------------------------------
DROP TABLE IF EXISTS shop.orders;
CREATE TABLE IF NOT EXISTS shop.orders (
    id         INT         NOT NULL AUTO_INCREMENT,
    user_id     INT         NOT NULL,
    created_at Timestamp   NOT NULL,
    price      DOUBLE      NOT NULL,
    PRIMARY KEY (id),
    UNIQUE INDEX IDX_ORDERS_ID_UNIQUE (id ASC),
    CONSTRAINT FK_ORDERS_USER_ID_USERS_ID
    FOREIGN KEY (user_id)
    REFERENCES shop.users (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
    );
--------------------------------------------------------
--  Table shop.order_lists
--------------------------------------------------------
DROP TABLE IF EXISTS shop.order_lists;
CREATE TABLE IF NOT EXISTS shop.order_lists (
    order_id   INT NOT NULL,
    product_id INT NOT NULL,
    PRIMARY KEY (order_id, product_id),
    CONSTRAINT FK_ORDERS_PRODUCTS_ORDER_ID_ORDERS_ID
    FOREIGN KEY (order_id)
    REFERENCES orders (id),
    CONSTRAINT FK_ORDERS_PRODUCTS_PRODUCT_ID_PRODUCTS_ID
    FOREIGN KEY (product_id)
    REFERENCES products (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
    );
--------------------------------------------------------
--  Table shop.images
--------------------------------------------------------
DROP TABLE IF EXISTS shop.images;
CREATE TABLE IF NOT EXISTS shop.images
(
    id           INT          NOT NULL AUTO_INCREMENT,
    image_path    VARCHAR(150) NOT NULL,
    category_id   INT          NULL,
    product_id    INT          NULL,
    primary_image INT          NOT NULL,
    PRIMARY KEY (id),
    UNIQUE INDEX IDX_IMAGES_ID_UNIQUE (id ASC),
    CONSTRAINT FK_IMAGES_CATEGORIES_ID_CATEGORIES_ID
    FOREIGN KEY (category_id)
    REFERENCES shop.categories (id),
    CONSTRAINT FK_IMAGES_PRODUCTS_ID_PRODUCTS_ID
    FOREIGN KEY (product_id)
    REFERENCES shop.products (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
    );

INSERT INTO shop.images (image_path, category_id, product_id, primary_image)
VALUES ('../../images/product/boardArduino.png', 1, null, 1);
INSERT INTO shop.images (image_path, category_id, product_id, primary_image)
VALUES ('../../images/product/kitArduino.png', 2, null, 1);
INSERT INTO shop.images (image_path, category_id, product_id, primary_image)
VALUES ('../../images/product/sensorArduino.png', 3, null, 1);
INSERT INTO shop.images (image_path, category_id, product_id, primary_image)
VALUES ('../../images/product/displaysAndIndicators.png', 4, null, 1);
INSERT INTO shop.images (image_path, category_id, product_id, primary_image)
VALUES ('../../images/product/tool.png', 5, null, 1);
INSERT INTO shop.images (image_path, category_id, product_id, primary_image)
VALUES ('../../images/product/smartHouse.png', 6, null, 1);
INSERT INTO shop.images (image_path, category_id, product_id, primary_image)
VALUES ('../../images/product/literature.png', 7, null, 1);


INSERT INTO shop.images (image_path, category_id, product_id, primary_image)
VALUES ('../../images/category/arduino/arduino_Uno_-_R3.png', null, 1, 1);
INSERT INTO shop.images (image_path, category_id, product_id, primary_image)
VALUES ('../../images/category/arduino/arduino_UNO_R3_1.png', null, 1, 0);
INSERT INTO shop.images (image_path, category_id, product_id, primary_image)
VALUES ('../../images/category/arduino/arduino_UNO_R3_2.png', null, 1, 0);

INSERT INTO shop.images (image_path, category_id, product_id, primary_image)
VALUES ('../../images/category/arduino/arduino_Mega.png', null, 2, 1);
INSERT INTO shop.images (image_path, category_id, product_id, primary_image)
VALUES ('../../images/category/arduino/arduino_Mega_1.png', null, 2, 0);
INSERT INTO shop.images (image_path, category_id, product_id, primary_image)
VALUES ('../../images/category/arduino/arduino_Mega_2.png', null, 2, 0);


INSERT INTO shop.images (image_path, category_id, product_id, primary_image)
VALUES ('../../images/category/kitArduino/scratchArduino.png', null, 3, 1);
INSERT INTO shop.images (image_path, category_id, product_id, primary_image)
VALUES ('../../images/category/kitArduino/basicSetArduino2.0.png', null, 4, 1);


INSERT INTO shop.images (image_path, category_id, product_id, primary_image)
VALUES ('../../images/category/sensorArduino/airQualitySensorV1.3.png', null, 5, 1);
INSERT INTO shop.images (image_path, category_id, product_id, primary_image)
VALUES ('../../images/category/sensorArduino/electricitySensor.png', null, 6, 1);


INSERT INTO shop.images (image_path, category_id, product_id, primary_image)
VALUES ('../../images/category/literature/learningArduino.png', null, 7, 1);
INSERT INTO shop.images (image_path, category_id, product_id, primary_image)
VALUES ('../../images/category/literature/knigaProektyViktorPetin.png', null, 8, 1);


INSERT INTO shop.images (image_path, category_id, product_id, primary_image)
VALUES ('../../images/category/tool/ZD-10D(12-0251).png', null, 9, 1);
INSERT INTO shop.images (image_path, category_id, product_id, primary_image)
VALUES ('../../images/category/tool/YIHUA908+.png', null, 10, 1);


INSERT INTO shop.images (image_path, category_id, product_id, primary_image)
VALUES ('../../images/category/displaysAndIndicators/Grove-4-DigitDisplay.png', null, 11, 1);
INSERT INTO shop.images (image_path, category_id, product_id, primary_image)
VALUES ('../../images/category/displaysAndIndicators/CharacterLCD2x16.png', null, 12, 1);


INSERT INTO shop.images (image_path, category_id, product_id, primary_image)
VALUES ('../../images/category/smartHouse/AqaraSmartDoorLockN100(Zigbee).png', null, 13, 1);
INSERT INTO shop.images (image_path, category_id, product_id, primary_image)
VALUES ('../../images/category/smartHouse/HubE1HE1-G01.png', null, 14, 1);

--------------------------------------------------------
--  Table shop.statistic
--------------------------------------------------------
DROP TABLE IF EXISTS shop.statistic;
CREATE TABLE IF NOT EXISTS shop.statistic
(
    id          INT          NOT NULL AUTO_INCREMENT,
    description VARCHAR(300) NOT NULL,
    PRIMARY KEY (id)
    );