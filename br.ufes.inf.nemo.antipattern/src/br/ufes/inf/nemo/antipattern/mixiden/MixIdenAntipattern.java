package br.ufes.inf.nemo.antipattern.mixiden;

import java.util.ArrayList;

import RefOntoUML.Mixin;
import RefOntoUML.Package;
import br.ufes.inf.nemo.antipattern.AntiPatternIdentifier;
import br.ufes.inf.nemo.antipattern.Antipattern;
import br.ufes.inf.nemo.antipattern.AntipatternInfo;
import br.ufes.inf.nemo.common.ontoumlparser.OntoUMLParser;

public class MixIdenAntipattern extends Antipattern<MixIdenOccurrence> {

	public MixIdenAntipattern(OntoUMLParser parser) throws NullPointerException {
		super(parser);
		// TODO Auto-generated constructor stub
	}
	
	public MixIdenAntipattern(Package pack) throws NullPointerException {
		super(pack);
		// TODO Auto-generated constructor stub
	}
	
	private static final String oclQuery =	
			"Mixin.allInstances()->select( m : Mixin |" +
			"		m.children()->size()>1" +
			"		and " +
			"		m.children()->forAll(child1, child2 : Classifier | " +
			"			child1<>child2 implies child1.allParents()->including(child1)->intersection(child2.allParents()->including(child2))->intersection(SubstanceSortal.allInstances())->size()=1))";
			//"->collect ( x | Tuple { mixin : Classifier = x, children : Set (Classifier) = x.children()})";
		 
		
	private static final AntipatternInfo info = new AntipatternInfo("Mixin with same Identity", 
			"MixIden", 
			"This anti-pattern occurs when a non-sortal («mixin» », «roleMixin», «category») is specialized only by types that follow the same identity principle. For that to happen, one of the following must hold:" +
			"\n\t ->All Subtypes are subkinds, phases and roles that share the same ultimate sortal supertype." +
			"\n\t ->Only one of the subtypes is a substance sortal (kind, quantity or collective) and the remainder subtypes are its subtypes.",
			MixIdenAntipattern.oclQuery); 
		
	public static AntipatternInfo getAntipatternInfo(){
		return info;
	}

	@Override
	public ArrayList<MixIdenOccurrence> identify() {
		ArrayList<Mixin> query_result;
		
		query_result = AntiPatternIdentifier.runOCLQuery(parser, oclQuery, Mixin.class);
		
		for (Mixin mixin : query_result) 
		{
			try {
				MixIdenOccurrence occurrence = new MixIdenOccurrence(mixin, this);
				super.occurrence.add(occurrence);
			} catch (Exception e) {
				System.out.println(info.getAcronym()+": Could not create occurrence!");
			}
		}
		
		return this.getOccurrences();
	}


}
