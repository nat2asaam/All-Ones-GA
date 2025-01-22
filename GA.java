package chapter2;
public class GA{
	private int populationSize;
	private double mutationRate;
	private double crossoverRate;
	private int elitismCount;
	public GA(int populationSize, double mutationRate,double crossoverRate, int elitismCount){
		this.populationSize=populationSize;
		this.mutationRate=mutationRate;
		this.crossoverRate=crossoverRate;
		this.elitismCount=elitismCount;
	}
	public Population initPopulation(int chromosomeLength){
		Population population= new Population(100,chromosomeLength);
		return population;
	}
	public boolean isTerminationConditionMet(Population population){
		for(Individual individual: population.getIndividuals()){
			if(individual.getFitness()==1){
				return true;
			}
		}
		return false;
	}
	public Individual selectParent(Population population){
		Individual individuals[]=population.getIndividuals();
		double populationFitness=population.getPopulationFitness();
		double rouletteWheelPosition=Math.random()*populationFitness;
		double spinWheel=0;
		for(Individual individual : individuals){
			spinWheel+=individual.getFitness();
			if(spinWheel>=rouletteWheelPosition){
				return individual;
			}
		}
		return individuals[population.size()-1];
	}
	public Population crossoverPopulation(Population population){
		Population newPopulation= new Population(population.size());
		for(int populationIndex=0;populationIndex<population.size();populationIndex++){
			Individual parent1=population.getFittest(populationIndex);
			if(this.crossoverRate>Math.random()&& populationIndex>this.elitismCount){
				Individual offspring=new Individual(parent1.getChromosomeLength());
				Individual parent2=selectParent(population);
				for(int geneIndex=0;geneIndex<parent1.getChromosomeLength();geneIndex++){
					if(0.5>Math.random()){
						offspring.setGene(geneIndex,parent1.getGene(geneIndex));
					}
					else{
						offspring.setGene(geneIndex,parent2.getGene(geneIndex));
					}
				}
				newPopulation.setIndividual(populationIndex,offspring);
			}
			else{
				newPopulation.setIndividual(populationIndex,parent1);
			}
		}
		return newPopulation;
	}
	public Population mutatePopulation(Population population){
		Population newPopulation=new Population(this.populationSize);
		for(int populationIndex=0 ;populationIndex<population.size();populationIndex++){
			Individual individual=population.getFittest(populationIndex);
			for(int geneIndex=0;geneIndex<individual.getChromosomeLength();geneIndex++){
				if(populationIndex>=this.elitismCount){
					int newGene=0;
					if(this.mutationRate > Math.random()){
						newGene=1;
						if(individual.getGene(geneIndex)==1){
							newGene=0;
						}
						else if(individual.getGene(geneIndex)==0){
							newGene=1;
						}
					}
					individual.setGene(geneIndex,newGene);

				}
			}
			newPopulation.setIndividual(populationIndex,individual);
		}
		return newPopulation;
	}
}