-- 年级信息表
CREATE TABLE IF NOT EXISTS `sys_grade` (
  `id`          bigint(20) unsigned NOT NULL,
  `name`      varchar(45) NOT NULL,
  `create_time` datetime            NOT NULL,
  `update_time` datetime            NOT NULL,
  `version` bigint(20) unsigned NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

INSERT INTO `sys_grade` (`id`, `name`, `create_time`, `update_time`, `version`) VALUES
(1, '一年级',  now(), now(), 1),
(2, '二年级', now(), now(), 1);

-- 班级信息表
CREATE TABLE IF NOT EXISTS `sys_class` (
  `id`          bigint(20) unsigned NOT NULL,
  `grade_id`     bigint(20) unsigned        NOT NULL,
  `name`      varchar(45) NOT NULL,
  `create_time` datetime            NOT NULL,
  `update_time` datetime            NOT NULL,
  `version` bigint(20) unsigned NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

INSERT INTO `sys_class` (`id`, `grade_id`, `name`, `create_time`, `update_time`, `version`) VALUES
(1, 1, '一(1)级',  now(), now(), 1),
(2, 1, '一(2)级',  now(), now(), 1),
(3, 2, '二(1)级',  now(), now(), 1),
(4, 2, '二(2)级',  now(), now(), 1);

-- 学科信息表
CREATE TABLE IF NOT EXISTS `sys_subject` (
  `id`          bigint(20) unsigned NOT NULL,
  `name`      varchar(45) NOT NULL,
  `create_time` datetime            NOT NULL,
  `update_time` datetime            NOT NULL,
  `version` bigint(20) unsigned NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

INSERT INTO `sys_subject` (`id`, `name`, `create_time`, `update_time`, `version`) VALUES
(1, '语文',  now(), now(), 1),
(2, '数学', now(), now(), 1),
(3, '英语', now(), now(), 1);

-- 年级科目关联表
CREATE TABLE IF NOT EXISTS `sys_grade_subject` (
  `id`          bigint(20) unsigned NOT NULL,
  `grade_id`    bigint(20) unsigned        NOT NULL,
  `subject_id`  bigint(20) unsigned        NOT NULL,
  `create_time` datetime            NOT NULL,
  `update_time` datetime            NOT NULL,
  `version` bigint(20) unsigned NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

INSERT INTO `sys_grade_subject` (`id`, `grade_id`, `subject_id`, `create_time`, `update_time`, `version`) VALUES
(1, 1, 1, now(), now(), 1),
(2, 1, 2, now(), now(), 1),
(3, 1, 3, now(), now(), 1),
(4, 2, 1, now(), now(), 1),
(5, 2, 2, now(), now(), 1),
(6, 2, 3, now(), now(), 1);

-- 学生信息表
CREATE TABLE IF NOT EXISTS `sys_student` (
  `id`          bigint(20) unsigned NOT NULL,
  `student_number`     varchar(45)        NOT NULL,
  `name`      varchar(45) NOT NULL,
  `sex`   varchar(45) NOT NULL,
  `age`   int(1),
  `password`        varchar(255),
  `class_id` bigint(20) unsigned  NOT NULL,
  `is_delete` tinyint(1) NOT NULL DEFAULT '0',
  `create_time` datetime            NOT NULL,
  `update_time` datetime            NOT NULL,
  `version` bigint(20) unsigned NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

INSERT INTO `sys_student` (`id`, `student_number`, `name`, `sex`, `age`, `class_id`, `create_time`, `update_time`, `version`) VALUES
(1, '10001', '张三', '男', 10,1, now(), now(), 1),
(2, '10002', '李四', '女', 10, 1,now(), now(), 1),
(3, '10003', 'kurt', '男', 10, 2,now(), now(), 1),
(4, '10004', 'test1', '女', 10, 2,now(), now(), 1),
(5, '10005', 'test2', '女', 10, 2,now(), now(), 1),
(6, '10006', 'test3', '女', 10, 3,now(), now(), 1);

-- 教师信息表
CREATE TABLE IF NOT EXISTS `sys_teacher` (
  `id`          bigint(20) unsigned NOT NULL,
  `teacher_number`     varchar(45)        NOT NULL,
  `name`      varchar(45) NOT NULL,
  `sex`   varchar(45) NOT NULL,
  `age`   int(1),
  `password`        varchar(255),
  `subject_id` bigint(20) unsigned NOT NULL,
  `is_delete` tinyint(1) NOT NULL DEFAULT '0',
  `create_time` datetime            NOT NULL,
  `update_time` datetime            NOT NULL,
  `version` bigint(20) unsigned NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

INSERT INTO `sys_teacher` (`id`, `teacher_number`, `name`, `sex`, `age`, `subject_id`, `create_time`, `update_time`, `version`) VALUES
(1, '20001', '语文老师', '男', 10, 1, now(), now(), 1),
(2, '20002', '数学老师', '女', 10, 2, now(), now(), 1),
(3, '20003', '英语老师', '男', 10, 3, now(), now(), 1);

-- 教师班级关联表
CREATE TABLE IF NOT EXISTS `sys_teacher_class` (
  `id`          bigint(20) unsigned NOT NULL,
  `teacher_number`   varchar(45)        NOT NULL,
  `class_id`  bigint(20) unsigned        NOT NULL,
  `create_time` datetime            NOT NULL,
  `update_time` datetime            NOT NULL,
  `version` bigint(20) unsigned NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

INSERT INTO `sys_teacher_class` (`id`, `teacher_number`, `class_id`, `create_time`, `update_time`, `version`) VALUES
(1, '20001', 1, now(), now(), 1),
(2, '20001', 2, now(), now(), 1),
(3, '20001', 3, now(), now(), 1),
(4, '20001', 4, now(), now(), 1),
(5, '20002', 1, now(), now(), 1),
(6, '20002', 2, now(), now(), 1),
(7, '20002', 3, now(), now(), 1),
(8, '20002', 4, now(), now(), 1),
(9, '20003', 1, now(), now(), 1),
(10, '20003', 2, now(), now(), 1),
(11, '20003', 3, now(), now(), 1),
(12, '20003', 4, now(), now(), 1);

-- 管理员信息表
CREATE TABLE IF NOT EXISTS `sys_manager` (
  `id`          bigint(20) unsigned NOT NULL,
  `manager_number`     varchar(45)        NOT NULL,
  `name`      varchar(45) NOT NULL,
  `password`        varchar(255),
  `create_time` datetime            NOT NULL,
  `update_time` datetime            NOT NULL,
  `version` bigint(20) unsigned NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- 试题信息表
CREATE TABLE IF NOT EXISTS `sys_question` (
  `id`          bigint(20) unsigned NOT NULL,
  `level`     tinyint(1)        NOT NULL default '0',
  `subject_id`     bigint(20) unsigned       NOT NULL,
  `type`     varchar(25)        NOT NULL,
  `tip`     varchar(45),
  `chapter`     varchar(45),
  `grade_id`     bigint(20) unsigned        NOT NULL,
  `content`     TEXT        NOT NULL,
  `answer`     varchar(45),
  `create_time` datetime            NOT NULL,
  `update_time` datetime            NOT NULL,
  `version` bigint(20) unsigned NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

INSERT INTO `sys_question` (`id`, `level`, `subject_id`, `type`, `grade_id`, `content`, `answer`, `create_time`, `update_time`, `version`) VALUES
(1, 1, 2, 0, 1, '选A', 'A', now(), now(), 1),
(2, 2, 2, 0, 1, '选B', 'B', now(), now(), 1);

-- 试卷信息表
CREATE TABLE IF NOT EXISTS `sys_paper` (
  `id`          bigint(20) unsigned NOT NULL,
  `level`     tinyint(1)        NOT NULL default '0',
  `subject_id`     bigint(20) unsigned        NOT NULL,
  `name`     varchar(25)        NOT NULL,
  `grade_id`     bigint(20) unsigned        NOT NULL,
  `is_delete` tinyint(1) NOT NULL DEFAULT '0',
  `is_publish` tinyint(1) NOT NULL DEFAULT '0',
  `create_time` datetime            NOT NULL,
  `update_time` datetime            NOT NULL,
  `version` bigint(20) unsigned NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

INSERT INTO `sys_paper` (`id`, `level`, `subject_id`, `name`, `grade_id`, `is_publish`, `create_time`, `update_time`, `version`) VALUES
(1, 1, 2, '数学试卷测试', 1, 1, now(), now(), 1);

-- 试卷试题关联表
CREATE TABLE IF NOT EXISTS `sys_paper_question` (
  `id`          bigint(20) unsigned NOT NULL,
  `index`     tinyint(1)        NOT NULL,
  `paper_id`     bigint(20) unsigned        NOT NULL,
  `content`     TEXT        NOT NULL,
  `answer`     varchar(45),
  `score`     tinyint(1) NOT NULL,
  `create_time` datetime            NOT NULL,
  `update_time` datetime            NOT NULL,
  `version` bigint(20) unsigned NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

INSERT INTO `sys_paper_question` (`id`, `index`, `paper_id`, `content`, `answer`, `score`, `create_time`, `update_time`, `version`) VALUES
(1, 1, 1, '选A', 'A', 50, now(), now(), 1),
(2, 2, 1, '选B', 'B', 50, now(), now(), 1);

-- 答卷表
CREATE TABLE IF NOT EXISTS `sys_paper_answer` (
  `id`          bigint(20) unsigned NOT NULL,
  `student_number`     varchar(45)        NOT NULL,
  `paper_id`     bigint(20) unsigned        NOT NULL,
  `index`     tinyint(1)        NOT NULL,
  `answer`     varchar(45),
  `score`     tinyint(1),
  `create_time` datetime            NOT NULL,
  `update_time` datetime            NOT NULL,
  `version` bigint(20) unsigned NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

INSERT INTO `sys_paper_answer` (`id`, `student_number`, `index`, `paper_id`, `answer`, `score`, `create_time`, `update_time`, `version`) VALUES
(1, '10001', 1, 1, 'A', 50, now(), now(), 1),
(2, '10001', 2, 1, 'B', 50, now(), now(), 1),
(3, '10002', 1, 1, 'A', 50, now(), now(), 1),
(4, '10002', 2, 1, 'A', 0, now(), now(), 1);

-- 成绩表
CREATE TABLE IF NOT EXISTS `sys_exam_result` (
  `id`          bigint(20) unsigned NOT NULL,
  `student_number`     varchar(45)        NOT NULL,
  `paper_id`     bigint(20) unsigned        NOT NULL,
  `score`     tinyint(1) NOT NULL DEFAULT '0',
  `create_time` datetime            NOT NULL,
  `update_time` datetime            NOT NULL,
  `version` bigint(20) unsigned NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;
