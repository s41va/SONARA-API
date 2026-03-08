package org.iesalixar.daw2.sdr.dwese2526_Sonara_api_sdr.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@AllArgsConstructor
@Data
@NoArgsConstructor
@Entity
@ToString(exclude = {"genero"})
@Table(name = "artista")
public class Artista {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", nullable = false, unique = true, length = 50)
    private String nombre;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "pais_origen",nullable = false, length = 100)
    private String pais;

    @Column(name = "descripcion", nullable = false, length = 400)
    private String descripcion;

    @ManyToOne(fetch = FetchType.EAGER) // Generalmente se carga inmediatamente
    @JoinColumn(name = "genero_id") // Esta es la columna FK en la tabla 'artista'
    private Genero genero;

    public Artista(String nombre, String pais, String descripcion) {
        this.nombre = nombre;
        this.pais = pais;
        this.descripcion = descripcion;
    }
}
