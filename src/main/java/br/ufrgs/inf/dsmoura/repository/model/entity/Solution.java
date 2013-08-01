package br.ufrgs.inf.dsmoura.repository.model.entity;

import java.io.Serializable;
import java.lang.Integer;
import javax.persistence.*;

@Entity
public class Solution implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name = "solution_generator", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.AUTO, generator="solution_generator")
	private Integer solutionPk;
	
	@OneToOne(cascade=CascadeType.ALL)
	private Requirements requirements = new Requirements();
	
	@OneToOne(cascade=CascadeType.ALL)
	private Analysis analysis = new Analysis();
	
	@OneToOne(cascade=CascadeType.ALL)
	private Design design = new Design();
	
	@OneToOne(cascade=CascadeType.ALL)
	private Implementation implementation = new Implementation();
	
	@OneToOne(cascade=CascadeType.ALL)
	private Test test = new Test();
	
	public Integer getSolutionPk() {
		return this.solutionPk;
	}

	public void setSolutionPk(Integer solutionPk) {
		this.solutionPk = solutionPk;
	}
	
	public Requirements getRequirements() {
		return requirements;
	}

	public void setRequirements(Requirements requirements) {
		this.requirements = requirements;
	}

	public Analysis getAnalysis() {
		return analysis;
	}

	public void setAnalysis(Analysis analysis) {
		this.analysis = analysis;
	}

	public Design getDesign() {
		return design;
	}

	public void setDesign(Design design) {
		this.design = design;
	}

	public Implementation getImplementation() {
		return implementation;
	}

	public void setImplementation(Implementation implementation) {
		this.implementation = implementation;
	}

	public Test getTest() {
		return test;
	}

	public void setTest(Test test) {
		this.test = test;
	}

}
