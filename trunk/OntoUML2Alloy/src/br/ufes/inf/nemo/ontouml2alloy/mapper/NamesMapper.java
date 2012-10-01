package br.ufes.inf.nemo.ontouml2alloy.mapper;

/**
 * Copyright 2011 NEMO (http://nemo.inf.ufes.br/en)
 *
 * This file is part of OntoUML2Alloy (OntoUML to Alloy Transformation).
 *
 * OntoUML2Alloy is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * OntoUML2Alloy is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with OntoUML2Alloy; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */

import java.util.HashMap;

import RefOntoUML.Association;
import RefOntoUML.Class;
import RefOntoUML.DataType;
import RefOntoUML.GeneralizationSet;
import RefOntoUML.Package;
import RefOntoUML.PackageableElement;
import RefOntoUML.PrimitiveType;
import RefOntoUML.Property;

/**
 *  This class is used to associate the Elements of the model with their modified name.
 *  
 * 	@author John Guerson 
 *  @author Tiago Sales 
 *  @author Lucas Thom   
 */

public class NamesMapper {

	/** Name of the RefOntoUML Model root. */
	public String refmodelname;
	
	/** Associate the Packageable Elements of the model with their modified names. */
	public HashMap<PackageableElement,String> elementsMap;
	
	/** Associate the Propeties of the model (AssociationEnds) with their modified names. */
	public HashMap<Property,String> assocEndMap;
	
	/** Performs modification on model name. */
	public NamesHandler h1;
	
	/** Performs modifications on names. */
	public NamesHandler h2;
		
	/**
	 * Constructor.
	 *  
	 * @param refmodel: The root of .refontouml model (RefOntoUML.Model).
	 */	 
	public NamesMapper(RefOntoUML.Model refmodel)
	{
		assocEndMap = new HashMap<Property,String>();
		elementsMap = new HashMap<PackageableElement,String>();
		
		h1 = new NamesHandler();
		this.refmodelname = h1.treatName(refmodel.getName(),refmodel.getClass().toString());
		
		h2 = new NamesHandler();
		initializeHashMaps(refmodel);		
	}
	
	/** 
	 * Initialize the Hash Maps.
	 * 
	 * @param rootpack: Usually the root of .refontouml model (RefOntoUML.Model).
	 */
	private void initializeHashMaps (PackageableElement rootpack) 
	{
		for(PackageableElement p : ((Package) rootpack).getPackagedElement())
		{
			if(p instanceof Package) 
				initializeHashMaps(p);
			else 
				addToHashMaps(p);			
		}
	}		
	
	/**
	 * Add packageable Element to the respective HashMap.
	 * 
	 * @param pe: A Packageable Element of the ontouml model.
	 */
	private void addToHashMaps(PackageableElement pe)
	{
		if (pe instanceof Class || pe instanceof Association || ((pe instanceof DataType) && !(pe instanceof PrimitiveType)) || (pe instanceof GeneralizationSet)) 
		{
			if(pe instanceof Association)
			{
				Property property0 = ((Association)pe).getOwnedEnd().get(0);
				Property property1 = ((Association)pe).getOwnedEnd().get(1);
				
				if( (property0.getName() != null) && !(property0.getName().equals("")) )
				{
					assocEndMap.put(property0, h2.treatName(property0.getName(),property0.getClass().toString()));
				}
				if( (property1.getName() != null) && !(property1.getName().equals("")) )
				{
					assocEndMap.put(property1, h2.treatName(property1.getName(),property1.getClass().toString()));
				}
			}
			
			elementsMap.put(pe,h2.treatName(pe.getName(),pe.getClass().toString()));			
		}				
	}
	
}
