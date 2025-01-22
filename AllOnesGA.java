package chapter2;
import chapter2.*;
public class AllOnesGA{
	GA ga;
	public AllOnesGA(){
		ga =new GA(100,0.51,0.9,2);
	}
	public static void main(String args[]){
		AllOnesGA allonesga=new AllOnesGA();	
		Population population=allonesga.ga.initPopulation(50);
		allonesga.evalPopulation(population);
		int generation=0;
		while(allonesga.ga.isTerminationConditionMet(population)==false){
			System.out.println("Best Solution: "+population.getFittest(0).toString());
			population=allonesga.ga.crossoverPopulation(population);
			population=allonesga.ga.mutatePopulation(population);
			allonesga.evalPopulation(population);
			generation++;
		}
		System.out.println("Found Solution in generation: "+generation);
		System.out.println("Best Solution: "+population.getFittest(0).toString());
	}
	public double calcFitness(Individual individual){
		int correctGenes=0;
		for(int geneIndex=0;geneIndex<individual.getChromosomeLength();geneIndex++){
			if(individual.getGene(geneIndex)==1){
				correctGenes++;
			}
		}
		double fitness=(double)correctGenes/individual.getChromosomeLength();
		individual.setFitness(fitness);
		return fitness;
	}
	public void evalPopulation(Population population){
		double populationFitness=0;
		for(Individual individual: population.getIndividuals()){
			populationFitness+=calcFitness(individual);
		}
		population.setPopulationFitness(populationFitness);
	}
}