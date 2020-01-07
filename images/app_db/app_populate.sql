\connect project_two

INSERT INTO public.unicorn_entity(
	name, color, entity_creator, has_wings, speed)
	VALUES 
('Sage Fair Ears', 'Pink', 'test', true, 150), 
('Thyme Celestial Tail', 'Orange', 'test', true, 100), 
('Quince Cheeky Pony', 'Black', 'test', true, 150), 
('Elm Jolly Reins', 'Pink', 'test', true, 150), 
('Grass Pied Ears', 'Red', 'test', true, 150),
('Dandelion Cheeky Saddle', 'Pink', 'test', true, 150),
('Sunflower Misty Foal', 'White', 'admin@admin.ch', true, 150), 
('Sage Rainbow Pony', 'Blue', 'admin@admin.ch', false, 150),
('Sage Sweet Tail', 'Purple', 'admin@admin.ch', false, 150), 
('Corn Candy Lord', 'Cyan', 'admin@admin.ch', true, 150);


INSERT INTO public.magic_entity(
	name, entity_creator, power, spell)
	VALUES 
('Fire strike', 'test', 50, 'Fire'), 
('Fire ball', 'test', 45, 'Fire'), 
('Water fall', 'test', 50, 'Water'), 
('Water bubble', 'test', 50, 'Water'), 
('Geyser', 'test', 50, 'Water'), 
('Tornado', 'test', 50, 'Wind'), 
('Earth ball', 'admin@admin.com', 50, 'Earth'), 
('Earth strike', 'admin@admin.com', 50, 'Earth'), 
('Lightning bolt', 'admin@admin.com', 50, 'lightning'), 
('Fire breath', 'admin@admin.com', 50, 'Fire');

INSERT INTO public.unicorn_entity_magic_entities(
	unicorn_entities_name, magic_entities_name)
	VALUES 
('Sage Fair Ears', 'Fire strike'), 
('Thyme Celestial Tail', 'Fire ball'),
('Quince Cheeky Pony', 'Water fall'), 
('Elm Jolly Reins', 'Water bubble'), 
('Grass Pied Ears', 'Geyser'), 
('Dandelion Cheeky Saddle', 'Tornado'), 
('Sunflower Misty Foal', 'Earth ball'), 
('Sage Rainbow Pony', 'Earth strike'), 
('Sage Sweet Tail', 'Lightning bolt'), 
('Corn Candy Lord', 'Fire breath');


