package com.springboot.app.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tipo_usuario", schema = "compra_venta")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TipoUsuario implements Serializable {

    private static final long serialVersionUID = -3453730571498730090L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tipo_usuario")
    private Long idTipoUsuario;

    @Column(name = "tipo_usuario")
    private String tipoUsuario;
}
