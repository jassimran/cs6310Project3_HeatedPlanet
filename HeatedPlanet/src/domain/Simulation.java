/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author jsoto
 */
@Entity
@Table(name = "simulation")
@NamedQueries({
    @NamedQuery(name = "Simulation.findAll", query = "SELECT s FROM Simulation s"),
    @NamedQuery(name = "Simulation.findById", query = "SELECT s FROM Simulation s WHERE s.id = :id"),
    @NamedQuery(name = "Simulation.findByName", query = "SELECT s FROM Simulation s WHERE s.name = :name"),
    @NamedQuery(name = "Simulation.findByAxialTilt", query = "SELECT s FROM Simulation s WHERE s.axialTilt = :axialTilt"),
    @NamedQuery(name = "Simulation.findByOrbitalEccentricity", query = "SELECT s FROM Simulation s WHERE s.orbitalEccentricity = :orbitalEccentricity"),
    @NamedQuery(name = "Simulation.findByTimeStep", query = "SELECT s FROM Simulation s WHERE s.timeStep = :timeStep"),
    @NamedQuery(name = "Simulation.findByLength", query = "SELECT s FROM Simulation s WHERE s.length = :length"),
    @NamedQuery(name = "Simulation.findByGridSpacing", query = "SELECT s FROM Simulation s WHERE s.gridSpacing = :gridSpacing")})
public class Simulation implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @Column(name = "axial_tilt")
    private double axialTilt;
    @Basic(optional = false)
    @Column(name = "orbital_eccentricity")
    private double orbitalEccentricity;
    @Basic(optional = false)
    @Column(name = "time_step")
    private int timeStep;
    @Basic(optional = false)
    @Column(name = "length")
    private int length;
    @Basic(optional = false)
    @Column(name = "grid_spacing")
    private int gridSpacing;
    @Basic(optional = false)
    @Column(name = "number_of_rows")
    private int numberOfRows;
    @Basic(optional = false)
    @Column(name = "number_of_columns")
    private int numberOfColumns;
    @Basic(optional = false)
    @Column(name = "precision")
    private int precision;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "simulation", fetch = FetchType.LAZY)
    private List<EarthGrid> timeStepList;
    @Basic(optional = true)
    @Column(name = "temporal_accuracy")
    private Integer temporalAccuracy;
    @Basic(optional = true)
    @Column(name = "geographic_accuracy")
    private Integer geoAccuracy;
    
    public Simulation() {
    }

    public Simulation(Integer id) {
        this.id = id;
    }

    public Simulation(Integer id, String name, double axialTilt, int orbitalEccentricity, int timeStep, int length, int gridSpacing, int numberOfRows, int numberOfColumns, int temporalAccuracy, int geoAccuracy, int precision) {
        this.id = id;
        this.name = name;
        this.axialTilt = axialTilt;
        this.orbitalEccentricity = orbitalEccentricity;
        this.timeStep = timeStep;
        this.length = length;
        this.gridSpacing = gridSpacing;
        this.numberOfColumns = numberOfRows;
        this.numberOfColumns = numberOfColumns;
        this.temporalAccuracy = temporalAccuracy;
        this.geoAccuracy = geoAccuracy;
        this.precision = precision;
    }

    
	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getAxialTilt() {
        return axialTilt;
    }

    public void setAxialTilt(double axialTilt) {
        this.axialTilt = axialTilt;
    }

    public double getOrbitalEccentricity() {
        return orbitalEccentricity;
    }

    public void setOrbitalEccentricity(double orbitalEccentricity) {
        this.orbitalEccentricity = orbitalEccentricity;
    }

    public int getTimeStep() {
        return timeStep;
    }

    public void setTimeStep(int timeStep) {
        this.timeStep = timeStep;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getGridSpacing() {
        return gridSpacing;
    }

    public void setGridSpacing(int gridSpacing) {
        this.gridSpacing = gridSpacing;
    }
    
    public int getNumberOfRows() {
        return numberOfRows;
    }

    public void setNumberOfRows(int numberOfRows) {
        this.numberOfRows = numberOfRows;
    }
    
    public int getNumberOfColumns() {
        return numberOfColumns;
    }

    public void setNumberOfColumns(int numberOfColumns) {
        this.numberOfColumns = numberOfColumns;
    }

    public List<EarthGrid> getTimeStepList() {
        return timeStepList;
    }

    public void setTimeStepList(List<EarthGrid> timeStepList) {
        this.timeStepList = timeStepList;
    }
    
    public Integer getTemporalAccuracy() {
		return temporalAccuracy;
	}

	public void setTemporalAccuracy(Integer temporalAccuracy) {
		this.temporalAccuracy = temporalAccuracy;
	}
	
    public Integer getGeoAccuracy() {
		return geoAccuracy;
	}

	public void setGeoAccuracy(Integer geoAccuracy) {
		this.geoAccuracy = geoAccuracy;
	}

    public int getPrecision() {
		return precision;
	}

	public void setPrecision(int precision) {
		this.precision = precision;
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
        if (!(object instanceof Simulation)) {
            return false;
        }
        Simulation other = (Simulation) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "domain.Simulation[ id=" + id + " ]";
    } 
}
