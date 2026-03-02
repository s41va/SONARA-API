

-- TABLA GENERO (Lado ONE de la relación)
CREATE TABLE IF NOT EXISTS genero (
     id INT AUTO_INCREMENT PRIMARY KEY,
     nombre VARCHAR(50) NOT NULL,
     descripcion VARCHAR(400)
);

-- TABLA ARTISTA (Lado MANY de la relación)
CREATE TABLE IF NOT EXISTS artista (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL UNIQUE,
    pais_origen VARCHAR(100),
    descripcion VARCHAR(400),

    -- Añadimos la columna de clave foránea
    genero_id INT NOT NULL,

    -- Definición de la clave foránea
    CONSTRAINT fk_artista_genero
        FOREIGN KEY (genero_id)
        REFERENCES genero(id)
        ON DELETE RESTRICT -- Evita eliminar un género si hay artistas asociados
        ON UPDATE CASCADE
);

-- La tabla artista_genero se ELIMINA ya que no es necesaria para 1:N.