-- 校园外卖系统 - 测试数据初始化脚本

-- 1. 插入测试用户（学生、商家、骑手）
INSERT INTO user (phone, username, password, name, role_type, status) VALUES
('13800138001', 'student001', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iDJfLqSsA9s0s5z7FZ5K5P5y5E5m', '张三', 1, 1),
('13800138002', 'student002', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iDJfLqSsA9s0s5z7FZ5K5P5y5E5m', '李四', 1, 1),
('13800138003', 'merchant001', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iDJfLqSsA9s0s5z7FZ5K5P5y5E5m', '王老板', 3, 1),
('13800138004', 'merchant002', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iDJfLqSsA9s0s5z7FZ5K5P5y5E5m', '赵老板', 3, 1),
('13800138005', 'rider001', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iDJfLqSsA9s0s5z7FZ5K5P5y5E5m', '骑手小王', 2, 1);

-- 2. 插入测试商家
INSERT INTO merchant (user_id, shop_name, shop_logo, shop_phone, shop_address, campus_area, 
                      business_hours_start, business_hours_end, min_delivery_amount, delivery_fee, 
                      shop_status, shop_notice) VALUES
(3, '美味快餐店', 'https://example.com/logo1.png', '13800138003', '江西财经大学南区食堂一楼', '南区', 
 '09:00:00', '21:00:00', 15.00, 3.00, 1, '欢迎光临，满 15 元起送！'),
(4, '香香小吃店', 'https://example.com/logo2.png', '13800138004', '江西财经大学北区食堂二楼', '北区', 
 '10:00:00', '22:00:00', 10.00, 2.00, 1, '小吃饮品，应有尽有！');

-- 3. 插入测试学生档案
INSERT INTO user_profile_student (user_id, student_id, college, major, grade, dormitory_building, dormitory_room) VALUES
(1, '2020001', '软件与通信工程学院', '软件工程', '2020 级', '南区 1 栋', '301'),
(2, '2021002', '会计学院', '会计学', '2021 级', '北区 5 栋', '402');

-- 4. 插入测试骑手档案
INSERT INTO user_profile_rider (user_id, real_name, id_card, vehicle_type, vehicle_number, work_status, total_orders, rating) VALUES
(5, '骑手小王', '360101200001011234', '电动车', '赣 A·E1234', 1, 0, 5.0);

-- 5. 插入测试商品（美味快餐店）
INSERT INTO product (merchant_id, name, description, image, price, original_price, category, stock, sold_count, status, sort_order) VALUES
(1, '宫保鸡丁饭', '经典川菜，鸡肉鲜嫩，花生香脆', 'https://example.com/product1.jpg', 18.00, 20.00, '主食', 100, 50, 1, 10),
(1, '鱼香肉丝饭', '酸甜微辣，开胃下饭', 'https://example.com/product2.jpg', 16.00, 18.00, '主食', 100, 30, 1, 9),
(1, '红烧狮子头饭', '肉质鲜美，入口即化', 'https://example.com/product3.jpg', 20.00, 22.00, '主食', 80, 20, 1, 8),
(1, '可乐', '冰镇可乐，快乐源泉', 'https://example.com/product4.jpg', 3.00, 3.50, '饮品', 200, 100, 1, 5),
(1, '豆浆', '现磨豆浆，营养健康', 'https://example.com/product5.jpg', 2.00, 2.50, '饮品', 150, 80, 1, 4);

-- 6. 插入测试商品（香香小吃店）
INSERT INTO product (merchant_id, name, description, image, price, original_price, category, stock, sold_count, status, sort_order) VALUES
(2, '烤冷面', '东北特色，外酥里嫩', 'https://example.com/product6.jpg', 8.00, 10.00, '小吃', 50, 60, 1, 10),
(2, '煎饼果子', '传统早餐，酥脆可口', 'https://example.com/product7.jpg', 6.00, 8.00, '小吃', 60, 45, 1, 9),
(2, '奶茶', '珍珠奶茶，香甜浓郁', 'https://example.com/product8.jpg', 5.00, 6.00, '饮品', 100, 90, 1, 8),
(2, '鸡米花', '金黄酥脆，香辣可口', 'https://example.com/product9.jpg', 10.00, 12.00, '小吃', 40, 35, 1, 7);

-- 7. 插入测试地址
INSERT INTO address_book (student_id, contact_name, contact_phone, campus_area, building, room, full_address, address_tag, is_default) VALUES
(1, '张三', '13800138001', '南区', '南区 1 栋', '301', '江西财经大学南区 1 栋 301 室', '宿舍', 1),
(2, '李四', '13800138002', '北区', '北区 5 栋', '402', '江西财经大学北区 5 栋 402 室', '宿舍', 1);

-- 8. 插入测试购物车项
INSERT INTO shopping_cart (student_id, merchant_id, product_id, product_name, product_image, product_price, quantity, selected) VALUES
(1, 1, 1, '宫保鸡丁饭', 'https://example.com/product1.jpg', 18.00, 1, 1),
(1, 1, 4, '可乐', 'https://example.com/product4.jpg', 3.00, 2, 1);

-- 说明：
-- 1. 所有用户的默认密码都是：123456（实际项目中应该加密存储）
-- 2. 可以使用这些测试数据进行接口测试
-- 3. 订单数据建议通过 API 创建，以便测试完整的订单流程
