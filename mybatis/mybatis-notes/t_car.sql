/*
 Navicat Premium Dump SQL

 Source Server         : mysql
 Source Server Type    : MySQL
 Source Server Version : 80407 (8.4.7)
 Source Host           : localhost:3306
 Source Schema         : mybatis

 Target Server Type    : MySQL
 Target Server Version : 80407 (8.4.7)
 File Encoding         : 65001

 Date: 13/11/2026 14:14:50
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
) ENGINE = InnoDB AUTO_INCREMENT = 23 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

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
INSERT INTO `t_car` VALUES (12, '津k88888', '路虎323', 40.50, '2024-10-24', NULL);
INSERT INTO `t_car` VALUES (13, '津k88888', '本田雅阁', 20.50, '2024-10-24', '新能源');
INSERT INTO `t_car` VALUES (14, '津b88888', '本田雅阁', 25.50, '2024-10-24', '新能源');
INSERT INTO `t_car` VALUES (16, '粤b88888', '本田雅阁', 27.50, '2024-10-24', '新能源');
INSERT INTO `t_car` VALUES (20, '成B22222', '长安333', 12.00, '2020-05-20', '电车');
INSERT INTO `t_car` VALUES (22, '莞B55588', '兰博基尼', 35.50, '2025-12-24', '新能源');

SET FOREIGN_KEY_CHECKS = 1;
