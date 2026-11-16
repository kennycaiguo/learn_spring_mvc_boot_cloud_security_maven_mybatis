/*
 Navicat Premium Dump SQL

 Source Server         : mysql
 Source Server Type    : MySQL
 Source Server Version : 80407 (8.4.7)
 Source Host           : localhost:3306
 Source Schema         : mybatis-advance

 Target Server Type    : MySQL
 Target Server Version : 80407 (8.4.7)
 File Encoding         : 65001

 Date: 15/11/2026 17:55:53
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_car
-- ----------------------------
DROP TABLE IF EXISTS `t_car`;
CREATE TABLE `t_car`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键自增',
  `car_num` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '车牌号',
  `brand` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '品牌',
  `guide_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '指导价：万',
  `produce_time` char(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '出厂日期',
  `car_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '汽车类型：燃油车 电车 氢能源',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 29 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_car
-- ----------------------------
INSERT INTO `t_car` VALUES (1, '京A88888', '宝驹535', 20.00, '2025-10-11', '燃油车');
INSERT INTO `t_car` VALUES (2, '京A66666', '劳斯莱斯泡影', 300.00, '2025-10-12', '新能源');
INSERT INTO `t_car` VALUES (3, '京A66688', '宝马535', 40.30, '2025-10-05', '氢能源');
INSERT INTO `t_car` VALUES (4, '京A66689', 'byd3003', 30.30, '2025-10-05', '新能源');
INSERT INTO `t_car` VALUES (5, '京A66689', 'byd3003', 30.30, '2025-10-05', '新能源');
INSERT INTO `t_car` VALUES (6, '深A66689', '劳斯莱斯666', 60.30, '2025-10-05', '新能源');
INSERT INTO `t_car` VALUES (7, '莞B55558', '暴龙555', 35.50, '2024-12-24', '新能源');
INSERT INTO `t_car` VALUES (8, '申A66600', '一汽大众', 20.30, '2025-10-05', '新能源');
INSERT INTO `t_car` VALUES (9, '沪A66600', '别克150', 30.30, '2025-10-05', '新能源');
INSERT INTO `t_car` VALUES (10, '津k88888', '路虎323', 40.50, '2024-10-24', '新能源');
INSERT INTO `t_car` VALUES (12, '津k88888', '路虎323', 40.50, '2024-10-24', '新能源');
INSERT INTO `t_car` VALUES (13, '津k88888', '本田雅阁', 20.50, '2024-10-24', '新能源');
INSERT INTO `t_car` VALUES (23, 'SK2001', '兰博基尼S', 20.00, '1998-10-11', '电车');
INSERT INTO `t_car` VALUES (24, 'SK2002', '兰博基尼M', 30.00, '1998-09-11', '新能源');
INSERT INTO `t_car` VALUES (25, 'SK2003', '兰博基尼L', 40.00, '1998-08-11', '燃油车');

-- ----------------------------
-- Table structure for t_clazz
-- ----------------------------
DROP TABLE IF EXISTS `t_clazz`;
CREATE TABLE `t_clazz`  (
  `cid` int NOT NULL,
  `cname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`cid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_clazz
-- ----------------------------
INSERT INTO `t_clazz` VALUES (1001, '高三1班');
INSERT INTO `t_clazz` VALUES (1002, '高三2班');

-- ----------------------------
-- Table structure for t_student
-- ----------------------------
DROP TABLE IF EXISTS `t_student`;
CREATE TABLE `t_student`  (
  `sid` int NOT NULL AUTO_INCREMENT,
  `sname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `cid` int NULL DEFAULT NULL,
  PRIMARY KEY (`sid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_student
-- ----------------------------
INSERT INTO `t_student` VALUES (1, '张三', 1001);
INSERT INTO `t_student` VALUES (2, '李四', 1001);
INSERT INTO `t_student` VALUES (3, '王五', 1001);
INSERT INTO `t_student` VALUES (4, '赵六', 1002);
INSERT INTO `t_student` VALUES (5, '钱七', 1002);

SET FOREIGN_KEY_CHECKS = 1;
