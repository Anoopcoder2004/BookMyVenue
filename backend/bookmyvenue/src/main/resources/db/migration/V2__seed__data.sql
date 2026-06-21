-- this is a work in progress
-- scripts are not working yet

-- 🔹 USERS
INSERT INTO users (id, name, email, password, role, is_blocked)
VALUES
(1, 'Admin User', 'admin1@gmail.com', 'password', 'ADMIN', false),
(2, 'John Owner', 'owner1@gmail.com', 'password', 'OWNER', false),
(3, 'Sarah Owner', 'owner2@gmail.com', 'password', 'OWNER', false),
(4, 'Normal User', 'user1@gmail.com', 'password', 'USER', false);

-----------------------------------------------------

-- 🔹 VENUES
INSERT INTO venues (id, name, description, city, address, price_per_day, capacity, status, owner_id)
VALUES
(1, 'Grand Ballroom', 'Elegant ballroom for weddings and galas', 'Mumbai', '12 Marine Drive', 15000, 500, 'APPROVED', 2),

(2, 'Royal Pavilion', 'Luxury outdoor venue with garden views', 'Delhi', '45 Connaught Place', 22000, 700, 'APPROVED', 2),

(3, 'Crystal Convention Center', 'Modern convention center for corporate events', 'Bangalore', '78 MG Road', 35000, 1000, 'PENDING', 3),

(4, 'Palm Beach Resort', 'Beachside venue perfect for destination weddings', 'Goa', '3 Calangute Beach Rd', 28000, 400, 'APPROVED', 3),

(5, 'Heritage Haveli', 'Traditional Rajasthani haveli with cultural decor', 'Jaipur', '19 Amber Fort Rd', 18000, 300, 'PENDING', 2);

-----------------------------------------------------

-- 🔹 VENUE IMAGES
INSERT INTO venue_images (id, image_url, venue_id)
VALUES
-- Grand Ballroom
(1, 'https://images.unsplash.com/photo-1519167758481-83f550bb49b3', 1),
(2, 'https://images.unsplash.com/photo-1505842465776-3d90f616310d', 1),

-- Royal Pavilion
(3, 'https://images.unsplash.com/photo-1520250497591-112f2f40a3f4', 2),
(4, 'https://images.unsplash.com/photo-1503428593586-e225b39bddfe', 2),

-- Crystal Center
(5, 'https://images.unsplash.com/photo-1542314831-068cd1dbfeeb', 3),

-- Beach Resort
(6, 'https://images.unsplash.com/photo-1501117716987-c8e1ecb210f1', 4),
(7, 'https://images.unsplash.com/photo-1505693416388-ac5ce068fe85', 4),

-- Heritage Haveli
(8, 'https://images.unsplash.com/photo-1520250497591-112f2f40a3f4', 5);

-----------------------------------------------------

-- 🔹 BOOKINGS
INSERT INTO bookings (id, booking_date, total_amount, status, user_id, venue_id)
VALUES
(1, '2026-07-10', 15000, 'CONFIRMED', 4, 1),
(2, '2026-07-15', 22000, 'PENDING', 4, 2),
(3, '2026-08-01', 28000, 'CONFIRMED', 4, 4);

-----------------------------------------------------

-- 🔹 PAYMENTS
INSERT INTO payments (id, amount, status, payment_gateway_id, booking_id)
VALUES
(1, 15000, 'SUCCESS', 'PAY123456', 1),
(2, 22000, 'INITIATED', 'PAY654321', 2),
(3, 28000, 'SUCCESS', 'PAY999888', 3);

-----------------------------------------------------

-- 🔥 FIX SEQUENCES (CRITICAL)
SELECT setval('users_id_seq', (SELECT MAX(id) FROM users));
SELECT setval('venues_id_seq', (SELECT MAX(id) FROM venues));
SELECT setval('venue_images_id_seq', (SELECT MAX(id) FROM venue_images));
SELECT setval('bookings_id_seq', (SELECT MAX(id) FROM bookings));
SELECT setval('payments_id_seq', (SELECT MAX(id) FROM payments));