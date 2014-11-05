/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.gatech.cs6310.project3.team17.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author jsoto
 */
@Entity
@Table(name = "time_step")
@NamedQueries({
    @NamedQuery(name = "TimeStep.findAll", query = "SELECT t FROM TimeStep t"),
    @NamedQuery(name = "TimeStep.findById", query = "SELECT t FROM TimeStep t WHERE t.id = :id"),
    @NamedQuery(name = "TimeStep.findByIndex", query = "SELECT t FROM TimeStep t WHERE t.index = :index"),
    @NamedQuery(name = "TimeStep.findBySimulatedDate", query = "SELECT t FROM TimeStep t WHERE t.simulatedDate = :simulatedDate")})
public class TimeStep implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "index")
    private int index;
    @Basic(optional = false)
    @Column(name = "simulated_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date simulatedDate;
    @JoinColumn(name = "simulation", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Simulation simulation;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "grid")
    private List<Node> nodeList;

    public TimeStep() {
    }

    public TimeStep(Integer id) {
        this.id = id;
    }

    public TimeStep(Integer id, int index, Date simulatedDate) {
        this.id = id;
        this.index = index;
        this.simulatedDate = simulatedDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Date getSimulatedDate() {
        return simulatedDate;
    }

    public void setSimulatedDate(Date simulatedDate) {
        this.simulatedDate = simulatedDate;
    }

    public Simulation getSimulation() {
        return simulation;
    }

    public void setSimulation(Simulation simulation) {
        this.simulation = simulation;
    }

    public List<Node> getNodeList() {
        return nodeList;
    }

    public void setNodeList(List<Node> nodeList) {
        this.nodeList = nodeList;
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
        if (!(object instanceof TimeStep)) {
            return false;
        }
        TimeStep other = (TimeStep) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "domain.TimeStep[ id=" + id + " ]";
    }
    
}
