package org.iesalixar.daw2.sdr.dwese2526_Sonara_api_sdr.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Entity
@ToString(exclude = {"artistas"})
@Table(name = "genero")
public class Genero {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", nullable = false, length = 50)
    private String nombre;

    @Column(name = "descripcion", nullable = true, length = 400)
    private String descripcion;

    @OneToMany(mappedBy = "genero", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Artista> artistas;

    public Genero(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }
    public Genero(String nombre, String descripcion, List<Artista> artista) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.artistas = artista;
    }
}
