package br.ufes.inf.nemo.ocl2swrl.factory.ocl.uml.impl;

import java.util.ArrayList;
import java.util.Set;

import org.eclipse.ocl.uml.impl.VariableExpImpl;
import org.eclipse.uml2.uml.internal.impl.NamedElementImpl;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.SWRLAtom;
import org.semanticweb.owlapi.model.SWRLDArgument;
import org.semanticweb.owlapi.model.SWRLVariable;

import br.ufes.inf.nemo.common.ontoumlparser.OntoUMLParser;
import br.ufes.inf.nemo.ocl2swrl.util.Util;



/**
 * @author fredd_000
 * @version 1.0
 * @created 24-set-2013 09:16:13
 */
public class VariableExpImplFactory extends OCLExpressionImplFactory {

	public VariableExpImplFactory(NamedElementImpl m_NamedElementImpl){
		super(m_NamedElementImpl);
	}
	
	public void finalize() throws Throwable {
		super.finalize();
	}

	@Override
	public ArrayList<SWRLDArgument> solve(String ctStereotype, OntoUMLParser refParser, String nameSpace, OWLOntologyManager manager, OWLDataFactory factory, OWLOntology ontology, Set<SWRLAtom> antecedent, Set<SWRLAtom> consequent, SWRLDArgument referredArgument, Boolean operatorNot, int repeatNumber, Boolean leftSideOfImplies) {
		VariableExpImpl variableExpImpl = (VariableExpImpl) this.m_NamedElementImpl;
		//String varName = variableExpImpl.getReferredVariable().getType().getName();
		String varName = Util.generateVarName(variableExpImpl.getReferredVariable().getType(), referredArgument);
		if(varName == null){
			varName = variableExpImpl.getName();
		}
		if(varName.equals("")){
			varName = variableExpImpl.getName();
		}
		if(repeatNumber > 1){
			varName += repeatNumber;
		}
		SWRLVariable var = factory.getSWRLVariable(IRI.create(nameSpace+varName));
		
		ArrayList<SWRLDArgument> retArgs = new ArrayList<SWRLDArgument>();
		retArgs.add(var);
		
		return retArgs;
	}
}