CREATE DATABASE IF NOT EXISTS speedfast_db;
USE speedfast_db;

DROP TABLE IF EXISTS entrega;
DROP TABLE IF EXISTS repartidor;
DROP TABLE IF EXISTS pedido;

CREATE TABLE repartidor (
    id INT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL
);

CREATE TABLE pedido (
    id INT PRIMARY KEY,
    direccion VARCHAR(150) NOT NULL,
    tipo VARCHAR(30) NOT NULL,
    estado VARCHAR(20) NOT NULL DEFAULT 'PENDIENTE'
);

CREATE TABLE entrega (
    id INT PRIMARY KEY,
    id_pedido INT NOT NULL,
    id_repartidor INT NOT NULL,
    fecha DATE NOT NULL,
    hora TIME NOT NULL,
    CONSTRAINT entrega_pedido_fk FOREIGN KEY (id_pedido) REFERENCES pedido(id),
    CONSTRAINT entrega_repartidor_fk FOREIGN KEY (id_repartidor) REFERENCES repartidor(id)
);
