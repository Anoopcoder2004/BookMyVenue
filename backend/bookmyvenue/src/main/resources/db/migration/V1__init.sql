
-- this is a work in progress
-- scripts are not working yet
-- 🔹 USERS
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,

    name VARCHAR(255),

    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,

    role VARCHAR(50),

    is_blocked BOOLEAN DEFAULT FALSE,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-----------------------------------------------------

-- 🔹 VENUES
CREATE TABLE venues (
    id BIGSERIAL PRIMARY KEY,

    name VARCHAR(255) NOT NULL,
    description VARCHAR(1000),

    city VARCHAR(255),
    address VARCHAR(255),

    price_per_day DOUBLE PRECISION,
    capacity INTEGER,

    status VARCHAR(50),

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    rejection_reason VARCHAR(500),

    owner_id BIGINT NOT NULL,

    CONSTRAINT fk_venue_owner
        FOREIGN KEY (owner_id)
        REFERENCES users(id)
        ON DELETE CASCADE
);

-----------------------------------------------------

-- 🔹 VENUE IMAGES
CREATE TABLE venue_images (
    id BIGSERIAL PRIMARY KEY,

    image_url TEXT,

    venue_id BIGINT NOT NULL,

    CONSTRAINT fk_image_venue
        FOREIGN KEY (venue_id)
        REFERENCES venues(id)
        ON DELETE CASCADE
);

-----------------------------------------------------

-- 🔹 BOOKINGS
CREATE TABLE bookings (
    id BIGSERIAL PRIMARY KEY,

    booking_date DATE,

    total_amount DOUBLE PRECISION,

    status VARCHAR(50),

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    user_id BIGINT NOT NULL,
    venue_id BIGINT NOT NULL,

    CONSTRAINT fk_booking_user
        FOREIGN KEY (user_id)
        REFERENCES users(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_booking_venue
        FOREIGN KEY (venue_id)
        REFERENCES venues(id)
        ON DELETE CASCADE
);

-----------------------------------------------------

-- 🔹 PAYMENTS
CREATE TABLE payments (
    id BIGSERIAL PRIMARY KEY,

    amount DOUBLE PRECISION,

    status VARCHAR(50),

    payment_gateway_id VARCHAR(255),

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    booking_id BIGINT UNIQUE,

    CONSTRAINT fk_payment_booking
        FOREIGN KEY (booking_id)
        REFERENCES bookings(id)
        ON DELETE CASCADE
);