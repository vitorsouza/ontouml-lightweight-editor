package br.ufes.inf.nemo.antipattern.wholeover;

import java.util.ArrayList;

import org.eclipse.emf.ecore.EObject;

import RefOntoUML.Classifier;
import RefOntoUML.Property;
import br.ufes.inf.nemo.antipattern.AntipatternOccurrence;
import br.ufes.inf.nemo.common.ontoumlparser.OntoUMLParser;

public class WholeOverOccurrence extends AntipatternOccurrence{

	Classifier whole;
	ArrayList<Property> partEnds;
	
	/*TODO: Adapt methods from RelOver to get supertypes...*/
	
	public WholeOverOccurrence(Classifier whole, ArrayList<Property> partEnds, OntoUMLParser parser) {
		super(parser);
		
		this.whole = whole;
		this.partEnds = partEnds;
	}

	@Override
	public OntoUMLParser setSelected() {
		ArrayList<EObject> selection = new ArrayList<EObject>();
		
		for (Property p : this.partEnds){
			selection.add(p.getType());
			selection.add(p.getAssociation());
		}
		
		selection.add(whole);
		
		parser.selectThisElements(selection,true);
		parser.autoSelectDependencies(OntoUMLParser.SORTAL_ANCESTORS, false);
		return parser;
	}
	
	@Override
	public String toString() {
		String result;
		
		result = "Whole: "+super.parser.getStringRepresentation(this.whole)+
				"\nParts: ";
				
		for (Property p : this.partEnds)
			result+="\n\t"+super.parser.getStringRepresentation(p.getType());
		/*
		result+="\nSupertypes: ";
		
		int i = 1;
		for (Classifier supertype : this.commonSupertypes.keySet()) {
			result += "\n\tSupertype #"+i+": "+super.parser.getStringRepresentation(supertype);
			for (Property p : this.commonSupertypes.get(supertype)) {
				result += "\n\t\t"+super.parser.getStringRepresentation(p);
			}
			
			i++;
		}*/
		return result;
	}
	

}
