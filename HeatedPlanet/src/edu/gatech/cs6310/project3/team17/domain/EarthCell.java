/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.gatech.cs6310.project3.team17.domain;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author jsoto
 */
@Entity
@Table(name = "node")
@NamedQueries({
    @NamedQuery(name = "EarthCell.findAll", query = "SELECT n FROM EarthCell n"),
    @NamedQuery(name = "EarthCell.findById", query = "SELECT n FROM EarthCell n WHERE n.id = :id"),
    @NamedQuery(name = "EarthCell.findByRow", query = "SELECT n FROM EarthCell n WHERE n.row = :row"),
    @NamedQuery(name = "EarthCell.findByColumn", query = "SELECT n FROM EarthCell n WHERE n.column = :column"),
    @NamedQuery(name = "EarthCell.findByTemperature", query = "SELECT n FROM EarthCell n WHERE n.temperature = :temperature")})
public class EarthCell implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "row")
    private int row;
    @Basic(optional = false)
    @Column(name = "column")
    private int column;
    @Basic(optional = false)
    @Column(name = "temperature")
    private double temperature;
    @JoinColumn(name = "grid", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private EarthGrid grid;

    public EarthCell() {
    }

    public EarthCell(Integer id) {
        this.id = id;
    }

    public EarthCell(Integer id, int row, int column, double temperature) {
        this.id = id;
        this.row = row;
        this.column = column;
        this.temperature = temperature;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public EarthGrid getGrid() {
        return grid;
    }

    public void setGrid(EarthGrid grid) {
        this.grid = grid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EarthCell)) {
            return false;
        }
        EarthCell other = (EarthCell) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "domain.Node[ id=" + id + " ]";
    }
    
}
