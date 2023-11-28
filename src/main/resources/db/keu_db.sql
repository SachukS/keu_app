-- Adminer 4.8.1 MySQL 5.7.39 dump

SET NAMES utf8;
SET time_zone = '+00:00';
SET foreign_key_checks = 0;
SET sql_mode = 'NO_AUTO_VALUE_ON_ZERO';

SET NAMES utf8mb4;

DROP TABLE IF EXISTS `deleted_militery_men`;
CREATE TABLE `deleted_militery_men`
(
    `id`                bigint(20)                              NOT NULL AUTO_INCREMENT,
    `death_certificate` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
    `delete_date`       datetime                                NOT NULL,
    `protokol_number`   varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
    `military_man_id`   bigint(20)                              NOT NULL,
    PRIMARY KEY (`id`),
    KEY `FK7puj23dm5x2jcr3vm2d9q3n0m` (`military_man_id`)
) ENGINE = MyISAM
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;


DROP TABLE IF EXISTS `family_members`;
CREATE TABLE `family_members`
(
    `id`              bigint(20)                              NOT NULL AUTO_INCREMENT,
    `birth_date`      datetime                                NOT NULL,
    `name`            varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
    `surname`         varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
    `third_name`      varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
    `military_man_id` bigint(20) DEFAULT NULL,
    `sex`             varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
    PRIMARY KEY (`id`),
    KEY `FKdmn5lr3y9vawbxjgq1o603sxq` (`military_man_id`)
) ENGINE = MyISAM
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

INSERT INTO `family_members` (`id`, `birth_date`, `name`, `surname`, `third_name`, `military_man_id`, `sex`)
VALUES (1, '1990-10-09 00:00:00', 'Олександр', 'Іванов', 'Вікторович', 1, 'MALE'),
       (2, '1995-10-09 00:00:00', 'Марина', 'Іванова', 'Вікторівна', 1, 'FEMALE');

DROP TABLE IF EXISTS `finance_sources`;
CREATE TABLE `finance_sources`
(
    `id`            bigint(20)                              NOT NULL AUTO_INCREMENT,
    `currency_type` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
    `name`          varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
    `sum`           double                                  NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE = MyISAM
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

INSERT INTO `finance_sources` (`id`, `currency_type`, `name`, `sum`)
VALUES (1, 'UAH', 'Державний бюджет', 1000000000);

DROP TABLE IF EXISTS `garrisons`;
CREATE TABLE `garrisons`
(
    `id`              bigint(20)                              NOT NULL AUTO_INCREMENT,
    `name`            varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
    `region`          varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
    `price_per_meter` double                                  NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE = MyISAM
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

INSERT INTO `garrisons` (`id`, `name`, `region`, `price_per_meter`)
VALUES (1, 'м.Київ', 'Київ', 20870),
       (2, 'Василькiв', 'Київська обл.', 19570),
       (3, 'Гостомель', 'Київська обл.', 19750),
       (4, 'Переяславський', 'Київська обл.', 18450),
       (5, 'Бровари', 'Київська обл.', 19200),
       (6, 'Бориспiль', 'Київська обл.', 19655),
       (7, 'Семиполки', 'Київська обл.', 18437);


DROP TABLE IF EXISTS `hibernate_sequence`;
CREATE TABLE `hibernate_sequence`
(
    `next_val` bigint(20) DEFAULT NULL
) ENGINE = MyISAM
  DEFAULT CHARSET = latin1;


DROP TABLE IF EXISTS `military_man`;
CREATE TABLE `military_man`
(
    `id`                          bigint(20)                              NOT NULL AUTO_INCREMENT,
    `accounting_date`             datetime                                NOT NULL,
    `address`                     varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
    `apartment_file_date`         datetime                                         DEFAULT NULL,
    `apartment_file_number`       varchar(255) COLLATE utf8mb4_unicode_ci          DEFAULT NULL,
    `death_date`                  datetime                                         DEFAULT NULL,
    `expected_compensation_value` double                                  NOT NULL,
    `family_war_2022`             bit(1)                                  NOT NULL DEFAULT b'0',
    `general_queue`               int(11)                                 NOT NULL,
    `info`                        longtext COLLATE utf8mb4_unicode_ci,
    `ipn`                         varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
    `name`                        varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
    `phone_number`                varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
    `provided`                    varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
    `quota_date`                  datetime                                         DEFAULT NULL,
    `quota_queue`                 int(11)                                 NOT NULL,
    `registrated`                 bit(1)                                  NOT NULL DEFAULT b'0',
    `room_count`                  int(11)                                 NOT NULL,
    `rozshirennya`                bit(1)                                  NOT NULL DEFAULT b'0',
    `service_from`                datetime                                NOT NULL,
    `service_until`               datetime                                         DEFAULT NULL,
    `surname`                     varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
    `third_name`                  varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
    `create_date`                 datetime                                NOT NULL,
    `want_compensation`           bit(1)                                  NOT NULL DEFAULT b'0',
    `quota_id`                    bigint(20)                                       DEFAULT NULL,
    `rank_id`                     bigint(20)                              NOT NULL,
    `work_id`                     bigint(20)                              NOT NULL,
    PRIMARY KEY (`id`),
    KEY `FK75ngois8jah3gvplbeo5t5urk` (`quota_id`),
    KEY `FK6lhun1i5dt8l6egcbom8y97tr` (`rank_id`),
    KEY `FK42jlfwxfgrxf53725safgrgyx` (`work_id`)
) ENGINE = MyISAM
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

INSERT INTO `military_man` (`id`, `accounting_date`, `address`, `apartment_file_date`, `apartment_file_number`,
                            `death_date`, `expected_compensation_value`, `family_war_2022`, `general_queue`, `info`,
                            `ipn`, `name`, `phone_number`, `provided`, `quota_date`, `quota_queue`, `registrated`,
                            `room_count`, `rozshirennya`, `service_from`, `service_until`, `surname`, `third_name`,
                            `create_date`, `want_compensation`, `quota_id`, `rank_id`, `work_id`)
VALUES (1, '1992-10-01 00:00:00', 'м. Київ, вул. Київська 1, кв. 1 ', '1992-09-01 00:00:00', '11',
        '2012-10-01 00:00:00', 0, CONV('0', 2, 10) + 0, 0, NULL, '1234567890', 'Борис', '1234567890', 'POST',
        '1992-10-01 00:00:00', 0, CONV('1', 2, 10) + 0, 0, CONV('0', 2, 10) + 0, '1982-10-01 00:00:00',
        '2023-10-01 00:00:00', 'Назаренко', 'Борисович', '2022-07-21 15:45:16', CONV('0', 2, 10) + 0, 1, 1, 1),
       (2, '1992-10-01 00:00:00', 'м. Київ, вул. Київська 1, кв. 1 ', '1992-09-01 00:00:00', '12',
        '2012-10-01 00:00:00', 0, CONV('1', 2, 10) + 0, 0, NULL, '1234567899', 'Артем', '1234567845', 'COMP',
        '1992-10-01 00:00:00', 0, CONV('1', 2, 10) + 0, 0, CONV('0', 2, 10) + 0, '1982-10-01 00:00:00',
        '2023-10-01 00:00:00', 'Клопотенко', 'Кирилович', '2022-07-21 15:45:16', CONV('0', 2, 10) + 0, 2, 3, 1),
       (3, '1992-10-01 00:00:00', 'м. Київ, вул. Київська 1, кв. 1 ', '1992-09-01 00:00:00', '13', NULL, 0,
        CONV('1', 2, 10) + 0, 1, NULL, '1234567899', 'Артур', '1234567845', 'NO', '1992-10-01 00:00:00', 1,
        CONV('1', 2, 10) + 0, 3, CONV('0', 2, 10) + 0, '1982-10-01 00:00:00', '2023-10-01 00:00:00', 'Клопотенко',
        'Кирилович', '2022-07-21 15:45:16', CONV('0', 2, 10) + 0, 2, 3, 1),
       (4, '1992-10-01 00:00:00', 'м. Київ, вул. Київська 1, кв. 1 ', '1992-09-01 00:00:00', '14', NULL, 2000000,
        CONV('1', 2, 10) + 0, 2, NULL, '1234567809', 'Петро', '1234567845', 'NO', '1992-10-01 00:00:00', 2,
        CONV('1', 2, 10) + 0, 3, CONV('0', 2, 10) + 0, '1982-10-01 00:00:00', '2023-10-01 00:00:00', 'Петренко',
        'Артурович ', '2022-07-21 15:45:16', CONV('1', 2, 10) + 0, 7, 3, 1),
       (5, '1992-10-01 00:00:00', 'м. Київ, вул. Київська 1, кв. 1 ', '1992-09-01 00:00:00', '15', NULL, 2000000,
        CONV('1', 2, 10) + 0, 3, NULL, '1234567831', 'Артем', '1234567845', 'NO', NULL, 0, CONV('1', 2, 10) + 0, 0,
        CONV('0', 2, 10) + 0, '1982-10-01 00:00:00', NULL, 'Назаренко', 'Кирилович', '2022-07-21 15:45:16',
        CONV('1', 2, 10) + 0, NULL, 4, 4),
       (6, '1992-10-01 00:00:00', 'м. Київ, вул. Київська 1, кв. 1 ', '1992-09-01 00:00:00', '16',
        '2012-10-01 00:00:00', 2000000, CONV('1', 2, 10) + 0, 4, NULL, '1234567831', 'Кирило', '1234567845', 'NO',
        '1992-10-01 00:00:00', 3, CONV('1', 2, 10) + 0, 0, CONV('0', 2, 10) + 0, '1982-10-01 00:00:00',
        '2023-10-01 00:00:00', 'Борисенко', 'Кирилович', '2022-07-21 15:45:16', CONV('1', 2, 10) + 0, 1, 4, 4);

DROP TABLE IF EXISTS `provided_flats`;
CREATE TABLE `provided_flats`
(
    `id`                   bigint(20) NOT NULL AUTO_INCREMENT,
    `cost`                 double     NOT NULL,
    `room_count`           int(11)    NOT NULL,
    `square`               double     NOT NULL,
    `unserviced_apartment` bit(1)     NOT NULL DEFAULT b'0',
    `finance_source_id`    bigint(20) NOT NULL,
    PRIMARY KEY (`id`),
    KEY `FK570s3g0lhcd3j59dymfbu312h` (`finance_source_id`)
) ENGINE = MyISAM
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

INSERT INTO `provided_flats` (`id`, `cost`, `room_count`, `square`, `unserviced_apartment`, `finance_source_id`)
VALUES (1, 200000, 2, 54, 0, 1);

DROP TABLE IF EXISTS `quotas`;
CREATE TABLE `quotas`
(
    `id`         bigint(20) NOT NULL,
    `name`       varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
    `short_name` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
    `type`       varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `UK_ila3wf7fl9a6s01jjycalvbai` (`name`),
    UNIQUE KEY `UK_99t8qjmecclm7y2hg56gp0gwb` (`short_name`)
) ENGINE = MyISAM
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

INSERT INTO `quotas` (`id`, `name`, `short_name`, `type`)
VALUES (0, 'Без пiльг', 'Без пiльг', 'NONE'),
       (1, 'Учасник лiквiдацiї аварiї на ЧАЕС I категорiї', 'ЧАЕС I кат.', 'OUTOFQUEUE'),
       (2, 'Учасник лiквiдацiї аварiї на ЧАЕС II категорiї', 'ЧАЕС II кат.', 'OUTOFQUEUE'),
       (3, 'ВЛК i ВТЕК', 'ВЛК i ВТЕК', 'FIRSTINPRIORITY'),
       (4, 'Учасник бойових дiй', 'Учасник б/д', 'FIRSTINPRIORITY'),
       (5, 'Багатодiтна сiм\'я', 'Багатодiт.сем.', 'FIRSTINPRIORITY'),
       (6, 'Iнвалiд 1-2 групи', 'Iнвал. 1-2 гр.', 'FIRSTINPRIORITY'),
       (7, 'Звiльнений за станом здоровя (скороч. штатiв)', 'Зв.за ст. зд.', 'OUTOFQUEUE'),
       (8, 'Одинока мати', 'Одинока мати', 'FIRSTINPRIORITY'),
       (9, 'ЛПС, ПСПЧ, БЧ', 'ЛПС,ПСПЧ,БЧ', 'FIRSTINPRIORITY'),
       (10, 'Вислуга 25 календарних рокiв', '25 календ. рок', 'FIRSTINPRIORITY'),
       (11, 'Поранений', 'Поранений', 'FIRSTINPRIORITY'),
       (12, 'Звiльнений ветеран вiйськової служби', 'Зв.вет.в.сл.', 'OUTOFQUEUE'),
       (13, 'Вiйськовослужбовцi вiйськових прокуратур', 'Вiйськ. прокур', 'FIRSTINPRIORITY'),
       (14, 'учасник бойових дiй (АТО)', 'учасник БД АТО', 'FIRSTINPRIORITY'),
       (15, 'В.служб.якi загинули(померли)', 'Померлi в.служ', 'OUTOFQUEUE'),
       (16, 'Iнвалiд вiйни 1 гр.', 'Iнв.вiйни 1гр', 'OUTOFQUEUE'),
       (17, 'Iнвалiд вiйни', 'Iнвалiд вiйни', 'OUTOFQUEUE'),
       (18, 'Педагогiчний та науково-педагогiчний працiвник', 'Пед.наук/прац.', 'FIRSTINPRIORITY'),
       (19, 'Звiльнений ветеран з iнших гарнiзонiв', 'звiл iнш.г-нiв', 'FIRSTINPRIORITY'),
       (20, 'Iнвалiд 1-2 гр (iнв.пов\'язана iз прох.в/с)', 'Iнв.1-2г(в/с)', 'OUTOFQUEUE'),
       (21, 'ЧАЕС I категорiя, що отримав пiльгу пiсля II категорiї', 'ЧАЕС2+ЧАЕС1', 'OUTOFQUEUE'),
       (22, 'Iнвалiд 2-3 групи', 'Iнвал.2-3  гр.', 'FIRSTINPRIORITY'),
       (23, 'суддя', 'суддя', 'OUTOFQUEUE'),
       (24, 'інв/ато/оос', 'інв/ато/оос', 'OUTOFQUEUE');

DROP TABLE IF EXISTS `ranks`;
CREATE TABLE `ranks`
(
    `id`         bigint(20)                              NOT NULL,
    `name`       varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
    `short_name` varchar(20) COLLATE utf8mb4_unicode_ci  NOT NULL,
    `type`       varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE = MyISAM
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

INSERT INTO `ranks` (`id`, `name`, `short_name`, `type`)
VALUES (0, 'п/п-к юст.зап.', 'п/п-к юст.', 'RESERVEMILITARY'),
       (1, 'працівник ЗСУ', 'пр.ЗСУ', 'NONMILITARY'),
       (2, 'рядовий за контракт.', 'ряд.', 'PRESENTMILITARY'),
       (3, 'солдат за контрактом', 'солдат', 'PRESENTMILITARY'),
       (4, 'ст.солдат за контр.', 'ст.солд.', 'PRESENTMILITARY'),
       (6, 'сім\'я загиблого в-сл', 'загиблі', 'PRESENTMILITARY'),
       (7, 'мл.сержант', 'мл.с-т', 'PRESENTMILITARY'),
       (8, 'сержант', 'с-т', 'PRESENTMILITARY'),
       (9, 'ст.сержант', 'ст.с-т', 'PRESENTMILITARY'),
       (10, 'старшина', 'с-на', 'PRESENTMILITARY'),
       (11, 'гл.старшина', 'гл.с-на', 'PRESENTMILITARY'),
       (12, 'прапорщик', 'пр-к', 'PRESENTMILITARY'),
       (13, 'мичман', 'м-н', 'PRESENTMILITARY'),
       (14, 'ст.прапорщик', 'ст.пр-к', 'PRESENTMILITARY'),
       (15, 'ст.мичман', 'ст.м-н', 'PRESENTMILITARY'),
       (16, 'мл.лейтенант', 'мл.л-т', 'PRESENTMILITARY'),
       (17, 'лейтенант', 'л-т', 'PRESENTMILITARY'),
       (18, 'лейтенант м/с', 'л-т м/с', 'PRESENTMILITARY'),
       (19, 'лейтенант юст.', 'л-т юст.', 'PRESENTMILITARY'),
       (20, 'ст.лейтенант', 'ст.л-т', 'PRESENTMILITARY'),
       (21, 'ст.лейтенант м/с', 'ст.л-т м/с', 'PRESENTMILITARY'),
       (22, 'ст.лейтенант юст.', 'ст.л-т юст', 'PRESENTMILITARY'),
       (23, 'капітан', 'к-н', 'PRESENTMILITARY'),
       (24, 'капітан м/с', 'к-н м/с', 'PRESENTMILITARY'),
       (25, 'капітан юст.', 'к-н юст.', 'PRESENTMILITARY'),
       (26, 'капітан-лейтенант', 'к-н/л-т', 'PRESENTMILITARY'),
       (27, 'майор', 'м-р', 'PRESENTMILITARY'),
       (28, 'майор м/с', 'м-р м/с', 'PRESENTMILITARY'),
       (29, 'майор юст.', 'м-р юст.', 'PRESENTMILITARY'),
       (30, 'капітан 3 ранга', 'к-н 3 р.', 'PRESENTMILITARY'),
       (31, 'підполковник', 'п/п-к', 'PRESENTMILITARY'),
       (32, 'підполковник м/с', 'п/п-к м/с', 'PRESENTMILITARY'),
       (33, 'підполковник юст.', 'п/п-к юст.', 'PRESENTMILITARY'),
       (34, 'капітан 2 ранга', 'к-н 2 р.', 'PRESENTMILITARY'),
       (35, 'полковник', 'п-к', 'PRESENTMILITARY'),
       (37, 'полковник юст.', 'п-к юст.', 'PRESENTMILITARY'),
       (38, 'капітан 1 ранга', 'к-н 1 р.', 'PRESENTMILITARY'),
       (39, 'генерал-майор', 'г-л/м-р', 'PRESENTMILITARY'),
       (40, 'контр-адмірал', 'контр-адм.', 'PRESENTMILITARY'),
       (41, 'генерал-лейтенант', 'г-л/л-т', 'PRESENTMILITARY'),
       (42, 'віце-адмірал', 'віце-адм.', 'PRESENTMILITARY'),
       (43, 'генерал-полковник', 'г-л/п-к', 'PRESENTMILITARY'),
       (44, 'рядовий за конт. зап', 'ряд. зап.', 'RESERVEMILITARY'),
       (45, 'майор запасу', 'майор з-су', 'RESERVEMILITARY'),
       (46, 'прапорщик запасу', 'пр-к з-су', 'RESERVEMILITARY'),
       (47, 'підполковник запасу', 'п/пк з-зсу', 'RESERVEMILITARY'),
       (48, 'полковник запасу', 'п-к з-су', 'RESERVEMILITARY'),
       (49, 'капітан запасу', 'к-н з-су', 'RESERVEMILITARY'),
       (50, 'полковник мед. сл', 'п-к м/с', 'PRESENTMILITARY'),
       (51, 'ст.прапорщик з/су', 'стпр-к з-с', 'RESERVEMILITARY'),
       (53, 'ст.лнт зап.', 'ст.л-нт за', 'RESERVEMILITARY'),
       (54, 'лейт.запасу', 'л-нт з/су', 'RESERVEMILITARY'),
       (55, 'полковник мед.сл.зап', 'п-к мед.сл', 'RESERVEMILITARY'),
       (56, 'підполковник запасу', 'п/п-к зап', 'RESERVEMILITARY'),
       (57, 'сержант запасу', 'с-нт зап', 'RESERVEMILITARY'),
       (58, 'п/п-к запасу', 'п/п-к з/су', 'RESERVEMILITARY'),
       (59, 'ген-л-нт запасу', 'г-л л-т з', 'RESERVEMILITARY'),
       (60, 'генерал армії', 'г-л арм.', 'PRESENTMILITARY'),
       (61, 'старшина запасу', 'ст-на з/су', 'RESERVEMILITARY'),
       (62, 'п-к юстиції запасу', 'п-к юст.', 'RESERVEMILITARY'),
       (63, 'п-к у відставці', 'п-к у відс', 'RESERVEMILITARY'),
       (64, 'сім\'я померлого в/сл', 'Померлі', 'PRESENTMILITARY'),
       (65, 'ст. лейтенант запасу', 'ст.л-нт за', 'RESERVEMILITARY'),
       (66, 'ст.пр-к у відставці', 'ст.пр-к', 'RESERVEMILITARY'),
       (67, 'солдат запасу', 'солд.зап', 'RESERVEMILITARY'),
       (68, 'ГЕНЕРАЛ', 'ГЕНЕРАЛ', 'PRESENTMILITARY'),
       (69, 'головний сержант', 'гол.с-нт', 'PRESENTMILITARY'),
       (70, 'головний сержант запасу', 'гол.с-нт.зап.', 'RESERVEMILITARY'),
       (71, 'штаб-сержант запасу', 'шт.с-нт.зап.', 'RESERVEMILITARY'),
       (72, 'штаб-сержант', 'шт.с-нт.', 'PRESENTMILITARY'),
       (73, 'майстер-сержант', 'май.с-нт.', 'PRESENTMILITARY'),
       (74, 'майстер-сержант запасу', 'май.с-нт.зап.', 'RESERVEMILITARY'),
       (75, 'старший майстер-сержант запасу', 'ст.май.с-нт.зап.', 'RESERVEMILITARY'),
       (76, 'старший майстер-сержант', 'ст.май.с-нт.', 'PRESENTMILITARY'),
       (77, 'головний майстер-сержант запасу', 'гол.май.с-нт.зап.', 'RESERVEMILITARY'),
       (78, 'головний майстер-сержант', 'гол.май.с-нт.', 'PRESENTMILITARY');

DROP TABLE IF EXISTS `registry`;
CREATE TABLE `registry`
(
    `id`               bigint(20) NOT NULL AUTO_INCREMENT,
    `receive_date`     datetime   NOT NULL,
    `received_money`   double     DEFAULT NULL,
    `military_man_id`  bigint(20) NOT NULL,
    `provided_flat_id` bigint(20) DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `FK1pfqlgpn98m03rx2isx507l0u` (`military_man_id`),
    KEY `FKpi60g23obcmrfrogmfhb5dvq6` (`provided_flat_id`)
) ENGINE = MyISAM
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

INSERT INTO `registry` (`id`, `receive_date`, `received_money`, `military_man_id`, `provided_flat_id`)
VALUES (1, '2022-09-20 00:00:00', 0.0, 1, 1),
       (2, '2021-09-23 00:00:00', 20000, 2, NULL);

DROP TABLE IF EXISTS `roles`;
CREATE TABLE `roles`
(
    `id`   int(11) NOT NULL AUTO_INCREMENT,
    `name` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = MyISAM
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;


DROP TABLE IF EXISTS `users`;
CREATE TABLE `users`
(
    `id`               bigint(20)                              NOT NULL AUTO_INCREMENT,
    `accounting_place` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
    `birth_date`       datetime                                NOT NULL,
    `create_date`      datetime                                NOT NULL,
    `garrison_id`      bigint(20)                              NOT NULL,
    `ipn`              varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
    `name`             varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
    `password`         varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
    `sex`              varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
    `surname`          varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
    `thirdname`        varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
    `update_date`      datetime                                NOT NULL,
    `military_man_id`  bigint(20)                              DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `FKiksavq8pxhtrmtvnchdlv0qek` (`garrison_id`),
    KEY `FKehlfny0wu3favyagoadstbe29` (`military_man_id`)
) ENGINE = MyISAM
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

INSERT INTO `users` (`id`, `accounting_place`, `birth_date`, `create_date`, `garrison_id`, `ipn`, `name`, `password`,
                     `sex`, `surname`, `thirdname`, `update_date`, `military_man_id`)
VALUES (1, 'ЖК ВІТІ', '1999-10-01 00:00:00', '2023-10-01 00:00:00', '1', '1234567861', 'Борис', '12345678',
        'MALE', 'Петренко', 'Миколайович', '2022-07-21 15:45:16', NULL),
       (2, 'ЖК ВІКНУ', '1999-10-01 00:00:00', '2023-10-01 00:00:00', '1', '1234567831', 'Артем', '12345678',
        'MALE', 'Назаренко', 'Кирилович', '2022-07-21 15:45:16', 4);

DROP TABLE IF EXISTS `user_roles`;
CREATE TABLE `user_roles`
(
    `user_id` bigint(20) NOT NULL,
    `role_id` int(11)    NOT NULL,
    PRIMARY KEY (`user_id`, `role_id`),
    KEY `FKh8ciramu9cc9q3qcqiv4ue8a6` (`role_id`)
) ENGINE = MyISAM
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;


DROP TABLE IF EXISTS `works`;
CREATE TABLE `works`
(
    `id`               bigint(20)                             NOT NULL AUTO_INCREMENT,
    `accounting_place` varchar(25) COLLATE utf8mb4_unicode_ci NOT NULL,
    `garrison_id`      bigint(20)                             NOT NULL,
    `work_place`       varchar(25) COLLATE utf8mb4_unicode_ci NOT NULL,
    PRIMARY KEY (`id`),
    KEY `FKhns7b0argu43tqhw7lu19n8o6` (`garrison_id`)
) ENGINE = MyISAM
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

INSERT INTO `works` (`id`, `accounting_place`, `garrison_id`, `work_place`)
VALUES (1, 'ЖК КСВ', '1', 'КСВ'),
       (2, 'КОб. Сил', '1', 'А0135'),
       (3, 'ЖК КССО', '1', 'А0987 вм №161'),
       (4, 'КВЗв\'язку', '1', 'КВЗв\'язку'),
       (8, 'ЖК КМедСил', '1', 'ГВКЦ'),
       (11, 'ЖК АМОУ та БПС', '1', 'А0615 (126ЦЗРМ)'),
       (17, 'ЖК НУОУ', '1', 'НУОУ'),
       (18, 'ЖК АМОУ та БПС', '1', 'Ансамбль'),
       (19, 'ЖК ГШ ЗСУ', '1', 'А2100  (ЦУВСП)'),
       (23, 'ЖК КСЛогістики', '1', 'Комвійськсполуч'),
       (25, 'ЖК АМОУ та БПС', '1', 'ВП'),
       (72, 'ЖК ККЕУ', '1', 'В/Прокуратури'),
       (73, 'ЖК ГШ ЗСУ', '1', 'ГУОП'),
       (74, 'ЖК КСВ', '1', 'МВК'),
       (77, 'ЖК НУОУ', '1', 'ЦВСД'),
       (78, 'ЖК ВIТI', '1', 'ВІТІ ім.Г.Крут'),
       (79, 'ЖК КСЛогістики', '1', 'КСЛогістики'),
       (84, 'ЖК КСВ', '1', 'КВЛ ім.Богуна'),
       (85, 'ЖК ККЕУ', '1', 'ККЕУ'),
       (88, 'ЖК КСВ', '1', 'ОВК'),
       (89, 'ЖК АМОУ та БПС', '1', 'Ред.\"Hар.армія\"'),
       (91, 'ЖК ГШ ЗСУ', '1', ' ЦНДІ'),
       (92, 'ЖК КСПідтримки', '1', 'А2330'),
       (93, 'ЖК АМОУ та БПС', '1', 'ЦСК МОУ'),
       (94, 'ЖК ВIТI', '1', 'в/к АМуніцУ'),
       (95, 'ЖК ККЕУ', '1', 'в/м 161'),
       (96, 'ЖК ВIТI', '1', 'в/к НТрУ'),
       (97, 'ЖК ВIКНУ', '1', 'ВІКНУ'),
       (98, 'ЖК А0799', '1', 'КВП НАУ'),
       (100, 'ЖК КМедСил', '1', 'УВМедА'),
       (101, 'ЖК ВIТI', '1', 'в/к НТУУ \"КПІ\"'),
       (102, 'ЖК ВIТI', '1', 'в/к АДПС Укр'),
       (103, 'ЖК ВIТI', '1', 'в/к НАгрУ'),
       (110, 'ЖК АМОУ та БПС', '1', 'ЦСБУ'),
       (115, 'ЖК КСЛогістики', '1', 'А0297'),
       (116, 'ЖК АМОУ та БПС', '1', 'ЦПІ МОУ'),
       (121, 'ЖК КСЛогістики', '1', 'А2788'),
       (123, 'ЖК КСВ', '1', 'А0222'),
       (124, 'ЖК ККЕУ', '1', '55127  (17ВА)'),
       (126, 'ЖК ККЕУ', '1', 'А2215 (Борисп)'),
       (129, 'ЖК ККЕУ', '1', 'А0138'),
       (130, 'ЖК А0799,А1236', '1', 'А0799,А1236'),
       (131, 'ЖК КВЗв\'язку', '1', 'А0168'),
       (132, 'ЖК ГШ ЗСУ', '1', 'А3521'),
       (133, 'ЖК ГШ ЗСУ', '1', 'ГУЛ'),
       (134, 'ЖК КВЗв\'язку', '1', 'А0106'),
       (135, 'ЖК КВЗв\'язку', '1', 'А0334'),
       (136, 'ЖК КВЗв\'язку', '1', 'А0476'),
       (137, 'ЖК ГШ ЗСУ', '1', 'А0488'),
       (138, 'ЖК ГШ ЗСУ', '1', 'ЦУБезп.'),
       (140, 'ЖК КСВ', '1', 'А2788'),
       (143, 'ЖК КСЛогістики', '1', 'А2070'),
       (148, 'ЖК КМедСил', '1', 'А1200(Ірпінь)'),
       (151, 'ЖК ККЕУ', '1', 'А2405 (розф)'),
       (153, 'ЖК Озброєння', '1', 'А0294'),
       (154, 'ЖК АМОУ та БПС', '1', 'Військо України'),
       (155, 'ЖК А0515 i ПВЧ', '1', 'А0515'),
       (157, 'ЖК ККЕУ', '1', 'А2161'),
       (160, 'ЖК ККЕУ', '1', 'А3918 (розф)'),
       (164, 'ЖК ККЕУ', '1', 'А1811 (розф)'),
       (165, 'ЖК ККЕУ', '1', 'п.37'),
       (170, 'ЖК Озброєння', '1', 'А2788'),
       (174, 'ЖК АМОУ та БПС', '1', 'ГУМР'),
       (175, 'ЖК ГШ ЗСУ', '1', 'А0565'),
       (176, 'ЖК АМОУ та БПС', '1', 'А4566'),
       (178, 'ЖК ККЕУ', '1', 'А0324(розф)'),
       (179, 'ЖК АМОУ та БПС', '1', 'МОУ'),
       (180, 'ЖК КСЛогістики', '1', 'Озброєння'),
       (181, 'ЖК ГШ ЗСУ', '1', 'ГШ ЗСУ'),
       (182, 'ЖК ГШ ЗСУ', '1', 'ООК ЗСУ'),
       (184, 'ЖК ГШ ЗСУ', '1', 'А0139'),
       (185, 'ЖК КВЗв\'язку', '1', 'А0351'),
       (186, 'ЖК АМОУ та БПС', '1', 'ЦЗСД МОУ'),
       (187, 'ЖК ГШ ЗСУ', '1', 'А0136'),
       (188, 'ЖК КОб.Сил', '1', 'А0135'),
       (189, 'ЖК КМедСил', '1', 'КМедСил'),
       (190, 'ЖК КСПідтримки', '1', 'А0602'),
       (191, 'ЖК КСПідтримки', '1', 'А0204'),
       (192, 'ЖК КСПідтримки', '1', 'А1423'),
       (193, 'ЖК КСПідтримки', '1', 'А1333'),
       (194, 'ЖК КСПідтримки', '1', 'РАЦ'),
       (195, 'КСЛогістики', '1', 'ЦУІІЗ'),
       (196, 'Всі', '1', 'Всі'),
       (197, 'ЖК КСВ', '1', 'A0105'),
       (201, 'в/ч А1789', '2', 'в/ч А1789'),
       (202, 'в/ч А0704', '2', 'в/ч А0704'),
       (203, 'в/ч А2682', '2', 'в/ч А2682'),
       (204, 'в/ч А1880', '2', 'в/ч А1880'),
       (206, 'в/ч А2860', '2', 'в/ч А2860'),
       (209, 'Обухівський РТЦК', '2', 'Обухівський РТЦК'),
       (210, 'А-0820', '2', 'А-0820'),
       (211, 'Iрпiн.Буч.ОМВК', '3', 'Ірпін.Буч.ОМВК'),
       (213, 'НУОУ', '4', 'НУОУ'),
       (214, 'А-0294', '2', 'А-0294'),
       (217, 'ОРВК', '2', 'ОРВК'),
       (218, 'в/ч А-0952', '2', 'в/ч А-0952'),
       (219, 'в/ч А-0565', '3', 'в/ч А-0565'),
       (221, 'в/ч А-2923', '3', 'в/ч А-2923'),
       (222, 'в/ч А-4180', '3', 'в/ч А-4180'),
       (223, 'Вишгород РВК', '3', 'Вишгород РВК'),
       (224, 'ФВП   НАДПС', '3', 'ФВП   НАДПС'),
       (225, 'в/ч А-2206', '5', 'в/ч А-2206'),
       (227, 'А-0706', '7', 'А-0706'),
       (229, 'А-0415', '7', 'А-0415'),
       (232, 'А-1525', '7', 'А-1525'),
       (233, 'в/ч А-3085', '4', 'в/ч А-3085'),
       (241, 'в/ч А-2860 Б', '5', 'в/ч А-2860 Б'),
       (243, 'в/ч А-2299', '5', 'в/ч А-2299'),
       (244, 'ОМВК 5', '5', 'ОМВК 5'),
       (245, 'в/ч А-0799 Б', '5', 'в/ч А-0799 Б'),
       (248, 'в/ч А-2215', '6', 'в/ч А-2215'),
       (249, 'ОМВК 6', '6', 'ОМВК Бориспіль'),
       (256, 'НОК КВЛ', '2', 'НОК КВЛ'),
       (259, 'в/ч А-0766', '5', 'в/ч А-0766'),
       (261, 'Бородян.РВК', '3', 'Бородян.РВК'),
       (262, 'в/ч  А2860 Г', '3', 'в/ч  А2860 Г'),
       (263, 'А-3796', '3', 'А-3796'),
       (268, 'в/ч А2206 №3', '3', 'в/ч А2206 №3'),
       (269, 'в/ч А3723', '3', 'в/ч А3723'),
       (270, 'в/ч А2860 С', '7', 'в/ч А2860 С'),
       (271, 'в/ч А0525', '5', 'в/ч А0525'),
       (272, 'Iван. ОРВК', '3', 'Іван. ОРВК'),
       (282, 'А2399', '4', 'А2399');

-- 2023-11-26 21:39:36
