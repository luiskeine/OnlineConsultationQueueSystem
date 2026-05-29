USE queue_system;
-- 1. Re-add the Staff and Client accounts
INSERT IGNORE INTO users (username, password, role) VALUES
('staff01', 'staff123', 'ADMIN'),
('staff02', 'staff123', 'ADMIN'),
('client01', 'pass123', 'CLIENT'),
('john_doe', 'pass123', 'CLIENT'),
('jane_smith', 'pass123', 'CLIENT'),
('bob_builder', 'pass123', 'CLIENT');
-- 2. Re-add the test tickets into the queue
-- (Note: qNum here follows the format we set in the code)
INSERT INTO appointments (name, concern, qNum, date, time, priority, status) VALUES
('john_doe', 'General Inquiry', 'T-001', 'Today', '09:00', 'Regular', 'Pending'),
('jane_smith', 'Payment Issue', 'T-002', 'Today', '09:15', 'Regular', 'Approved'),
('bob_builder', 'Emergency Repair', 'T-003', 'Today', '09:30', 'Emergency', 'Approved');
