-- Insertar datos en GENERO (sin cambios)

INSERT IGNORE INTO genero (id, nombre, descripcion) VALUES
(1,"Rap","Genero emergente de EEUU alrededor de los años 70, por la cultura afroamericana"),
(2, "Pop", "El genero Popular procedente del rock and roll"),
(3, "Reggaeton", "Caribeño surgio alrededor de los principios de los 90 en Puerto Rico y Panama"),
(4, 'Rock', 'Género musical surgido en los años 40 y 50, caracterizado por un ritmo enérgico y el uso de guitarras eléctricas.'),
(5, 'Electrónica', 'Música creada con instrumentos y tecnología electrónica, ideal para clubes y festivales.'),
(6, 'R&B', 'Rhythm and Blues. Género afroamericano que combina jazz, góspel y blues.');

-- Insertar datos en ARTISTA (Con la clave foránea genero_id)
-- Se asigna un UNICO genero_id a cada artista.
INSERT IGNORE INTO artista (nombre,password, pais_origen, descripcion, genero_id) VALUES
-- ID de Género elegido: 1=Rap, 2=Pop, 3=Reggaeton, 4=Rock, 5=Electrónica, 6=R&B
('Eminem','password', 'Estados Unidos', 'Famoso rapero blanco, reconocido por su lírica y velocidad.', 1),        -- Rap
('Bad Bunny', 'password','Puerto Rico', 'Icono global del trap y reggaetón, conocido por su estilo único.', 3),     -- Reggaeton (principal)
('Taylor Swift', 'password','Estados Unidos', 'Cantautora pop y country, una de las artistas más vendidas.', 2),   -- Pop
('Rosalía', 'password','España', 'Fusiona el flamenco con el pop urbano y el trap.', 2),                             -- Pop (principal)
('Drake', 'password','Canadá', 'Rapero, cantante y actor, dominante en las listas de streaming.', 1),               -- Rap
('J Balvin', 'password','Colombia', 'Pionero en llevar el reggaetón a la escena musical global.', 3),               -- Reggaeton
('Madonna', 'password','Estados Unidos', 'Reina del Pop, famosa por su reinvención constante.', 2),                 -- Pop
('Quevedo', 'password','España', 'Artista emergente de música urbana y reggaetón con gran éxito internacional.', 3), -- Reggaeton (principal)
('Kendrick Lamar','password', 'Estados Unidos', 'Considerado uno de los raperos más influyentes de su generación.', 1), -- Rap
('Rihanna', 'password','Barbados', 'Cantante de pop, R&B y dancehall, empresaria.', 2),                              -- Pop (principal)
('The Weeknd', 'password','Canadá', 'Famoso por fusionar R&B, pop y trap con letras oscuras.', 6),                   -- R&B (principal)
('Coldplay', 'password','Reino Unido', 'Banda de rock alternativo conocida por sus himnos de estadio.', 4),          -- Rock (principal)
('Calvin Harris', 'password','Reino Unido', 'DJ, productor y músico reconocido por la música electrónica de baile.', 5), -- Electrónica
('Aretha Franklin', 'password','Estados Unidos', 'Legendaria cantante y pianista, conocida como la "Reina del Soul".', 6), -- R&B
('Arctic Monkeys', 'password','Reino Unido', 'Banda de rock indie y post-punk, muy influyente en los 2000s.', 4);   -- Rock

-- Eliminada la inserción en artista_genero.