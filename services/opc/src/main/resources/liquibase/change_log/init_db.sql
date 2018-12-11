use jemmycloud_opc;


SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for pc_mq_message_data
-- ----------------------------
DROP TABLE IF EXISTS `mq_message_data`;
CREATE TABLE `mq_message_data` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `version` int(11) DEFAULT '0' COMMENT '版本号',
  `message_key` varchar(200) CHARACTER SET utf8 DEFAULT '' COMMENT '消息key',
  `message_topic` varchar(50) CHARACTER SET utf8 DEFAULT '' COMMENT 'topic',
  `message_tag` varchar(50) CHARACTER SET utf8 DEFAULT '' COMMENT 'tag',
  `message_body` longtext CHARACTER SET utf8 COMMENT '消息内容',
  `message_type` int(11) DEFAULT '10' COMMENT '消息类型: 10 - 生产者 ; 20 - 消费者',
  `delay_level` int(11) DEFAULT '0' COMMENT '延时级别 1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h',
  `order_type` int(11) DEFAULT '0' COMMENT '顺序类型 0有序 1无序',
  `status` int(11) DEFAULT '10' COMMENT '消息状态',
  `creator` varchar(20) CHARACTER SET utf8 DEFAULT '' COMMENT '创建人',
  `creator_id` bigint(20) DEFAULT NULL COMMENT '创建人ID',
  `created_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `last_operator` varchar(20) CHARACTER SET utf8 DEFAULT '' COMMENT '最近操作人',
  `last_operator_id` bigint(20) DEFAULT NULL COMMENT '最后操作人ID',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `yn` int(11) DEFAULT '0' COMMENT '是否删除 -0 未删除 -1 已删除',
  PRIMARY KEY (`id`),
  KEY `idx_message_key` (`message_key`) USING BTREE,
  KEY `idx_created_time` (`created_time`) USING BTREE,
  KEY `idx_update_time` (`update_time`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=318182082838798337 DEFAULT CHARSET=utf8mb4 COMMENT='消息记录表';

-- ----------------------------
-- Records of pc_mq_message_data
-- ----------------------------

-- ----------------------------
-- Table structure for pc_opc_sms_setting
-- ----------------------------
DROP TABLE IF EXISTS `opc_sms_setting`;
CREATE TABLE `opc_sms_setting` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `version` int(4) NOT NULL DEFAULT '0' COMMENT '版本号',
  `again_send_time` int(4) DEFAULT NULL COMMENT '可再次发送时间（毫秒）',
  `invalid_time` int(4) DEFAULT NULL COMMENT '失效时间（分钟）',
  `type` varchar(32) CHARACTER SET utf8 DEFAULT '' COMMENT '短信类型',
  `type_desc` varchar(100) CHARACTER SET utf8 DEFAULT '' COMMENT '类型描述',
  `templet_code` varchar(32) CHARACTER SET utf8 DEFAULT '' COMMENT '模板code',
  `templet_content` varchar(1000) CHARACTER SET utf8 DEFAULT '' COMMENT '模板内容',
  `send_max_num` int(4) DEFAULT NULL COMMENT '一天中可发送的最大数量',
  `ip_send_max_num` int(4) DEFAULT NULL COMMENT '一个IP一天中可发送的最大数量',
  `remark` varchar(200) CHARACTER SET utf8 DEFAULT '' COMMENT '备注',
  `creator` varchar(20) CHARACTER SET utf8 DEFAULT '' COMMENT '创建人',
  `creator_id` bigint(20) DEFAULT NULL COMMENT '创建人ID',
  `created_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `last_operator` varchar(20) CHARACTER SET utf8 DEFAULT '' COMMENT '最近操作人',
  `last_operator_id` bigint(20) DEFAULT NULL COMMENT '最后操作人ID',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `yn` int(11) DEFAULT '0' COMMENT '删除标识(1-已删除；0-未删除)',
  PRIMARY KEY (`id`),
  UNIQUE KEY `un_type` (`type`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COMMENT='短信模板设置表';

-- ----------------------------
-- Records of pc_opc_sms_setting
-- ----------------------------

-- ----------------------------
-- Table structure for pc_opt_attachment
-- ----------------------------
DROP TABLE IF EXISTS `opt_attachment`;
CREATE TABLE `opt_attachment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `ref_no` varchar(20) CHARACTER SET utf8 DEFAULT '' COMMENT '上传附件的相关业务流水号',
  `center_name` varchar(20) CHARACTER SET utf8 DEFAULT '' COMMENT '中心名称(英文简写)',
  `bucket_name` varchar(20) CHARACTER SET utf8 DEFAULT '' COMMENT '文件服务器根目录',
  `name` varchar(255) CHARACTER SET utf8 DEFAULT '' COMMENT '附件名称',
  `path` varchar(255) CHARACTER SET utf8 DEFAULT '' COMMENT '附件存储相对路径',
  `type` varchar(255) CHARACTER SET utf8 DEFAULT '' COMMENT '附件类型',
  `format` varchar(255) CHARACTER SET utf8 DEFAULT '' COMMENT '附件格式',
  `description` varchar(255) CHARACTER SET utf8 DEFAULT '' COMMENT '备注',
  `version` bigint(20) DEFAULT '0' COMMENT '版本号',
  `creator` varchar(50) CHARACTER SET utf8 DEFAULT '' COMMENT '创建人',
  `creator_id` bigint(20) DEFAULT NULL COMMENT '创建人ID',
  `created_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `last_operator` varchar(50) CHARACTER SET utf8 DEFAULT NULL COMMENT '最后操作人',
  `last_operator_id` bigint(20) DEFAULT NULL COMMENT '最后操作人ID',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=318168887994621953 DEFAULT CHARSET=utf8mb4 COMMENT='业务附件表';
