package br.ufes.inf.nemo.ontouml2uml;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.eclipse.emf.ecore.resource.Resource;

import RefOntoUML.parser.OntoUMLParser;
import br.ufes.inf.nemo.common.file.FileUtil;

/**
 * This class transforms OntoUML models into UML. It simply ignores the stereotypes of classes and relations.
 * The user can optionally ignore the package hierarchy or the derivation relationship in the transformation.
 * 
 * Furthermore, the user can additionally transform OntoUML into a Temporal UML model that includes UFO's
 * branching time structure. In this temporal UML model, each association of the OntoUML model is reified into a class.
 * This reified class in turn and every other top level class of the model is additionally linked to the worlds in which they exist.
 * Moreover, several world operations and existence operations were added to classes. This conversion is useful for
 * the Temporal OCL extension developed which uses this temporal UML model (in background) for manipulation of OCL constructs.
 *  
 * @author John Guerson
 *
 */
public class OntoUML2UML {
		 	  		
	public static String log = new String();
	private static OntoUML2UMLOption options = new OntoUML2UMLOption();	
	private static Transformator utransformer;		
	private static org.eclipse.uml2.uml.Package umlRoot;
	private static String umlPath = new String();
	
	private static Reificator tgenerator; 
	private static ProfileAplicator profileApplicator;
	
	//*****************************
	//Pure UML
	//*****************************
	
	public static Resource convertToUML (RefOntoUML.Package refmodel, String umlPath)
	{
		return convertToUML(refmodel, umlPath, options);
	}
	
	public static Resource convertToUML (OntoUMLParser refparser, String umlPath)
	{
		return convertToUML(refparser, umlPath, options);
	}
		
	public static Resource convertToUML (RefOntoUML.Package refmodel, String umlPath, OntoUML2UMLOption opt)
	{
		log="";
		options=opt;
		Resource umlResource = null;
		
		OntoUMLParser refparser = new OntoUMLParser(refmodel);
		
		utransformer = new Transformator(refparser,opt);			  
		org.eclipse.uml2.uml.Package umlmodel = utransformer.run();	
   		   
		OntoUML2UML.umlPath=umlPath;
		umlResource = OntoUML2UMLUtil.saveUML(umlPath,umlmodel);		   
		return umlResource;
	}
	
	public static org.eclipse.uml2.uml.Package convertToUMLNoSaving (RefOntoUML.Package refmodel, String umlPath, OntoUML2UMLOption opt)
	{
		log="";
		options=opt;
		
		OntoUMLParser refparser = new OntoUMLParser(refmodel);
		
		utransformer = new Transformator(refparser,opt);			  
		org.eclipse.uml2.uml.Package umlmodel = utransformer.run();	
   		   
		OntoUML2UML.umlPath=umlPath;				   
		return umlmodel;
	}
	
	public static Resource convertToUML (OntoUMLParser refparser, String umlPath, OntoUML2UMLOption opt)
	{
		log="";
		options=opt;
		Resource umlResource = null;
		
		utransformer = new Transformator(refparser,opt);			  
		org.eclipse.uml2.uml.Package umlmodel = utransformer.run();	
   		   
		OntoUML2UML.umlPath=umlPath;
		umlResource = OntoUML2UMLUtil.saveUML(umlPath,umlmodel);		   
		return umlResource;
	}	
	
	public static org.eclipse.uml2.uml.Package convertToUMLNoSaving (OntoUMLParser refparser, String umlPath, OntoUML2UMLOption opt)
	{
		log="";
		options=opt;
		
		utransformer = new Transformator(refparser,opt);			  
		org.eclipse.uml2.uml.Package umlmodel = utransformer.run();	
   		   
		OntoUML2UML.umlPath=umlPath;				   
		return umlmodel;
	}	
		
	//*****************************
	//UML Profile
	//*****************************

	public static Resource convertToUMLProfile (OntoUMLParser refparser, String umlPath)
	{
		return convertToUMLProfile(refparser, umlPath, options);
	}
	
	public static Resource convertToUMLProfile (RefOntoUML.Package refmodel, String umlPath)
	{
		return convertToUMLProfile(refmodel, umlPath, options);
	}
	
	public static void turnIntoUMLProfile(org.eclipse.uml2.uml.Package umlRoot, String umlPath)
	{	
		profileApplicator = new ProfileAplicator(umlRoot, umlPath, utransformer.getConverter().umap);
		profileApplicator.apply();
		try {
			profileApplicator.save();
			profileApplicator.resolveURIs();			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static Resource convertToUMLProfile (RefOntoUML.Package refmodel, String umlPath, OntoUML2UMLOption opt)
	{		
		umlRoot = convertToUMLNoSaving(refmodel, umlPath, opt);
		
		profileApplicator = new ProfileAplicator(umlRoot, umlPath, utransformer.getConverter().umap);
		profileApplicator.apply();
		log += profileApplicator.getLog();
		
		try {
			Resource result = profileApplicator.save();
			profileApplicator.resolveURIs();
			return result;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static Resource convertToUMLProfile(OntoUMLParser refparser, String umlPath, OntoUML2UMLOption opt)
	{
		umlRoot = convertToUMLNoSaving(refparser, umlPath, opt); 
		
		profileApplicator = new ProfileAplicator(umlRoot, umlPath, utransformer.getConverter().umap);
		profileApplicator.apply();		
		log += profileApplicator.getLog();
				
		try {
			Resource result = profileApplicator.save();
			profileApplicator.resolveURIs();
			return result;
		} catch (IOException e) {
			e.printStackTrace();
		}
				
		return null;		
	}
	
	//*****************************
	//Temporal pure UML
	//*****************************
	
	public static Resource convertToTemporalUML (OntoUMLParser refparser, String umlPath, boolean simplifiedVersion)
	{
		return convertToTemporalUML(refparser, umlPath, options, simplifiedVersion);
	}
	
	public static Resource convertToTemporalUML (RefOntoUML.Package refmodel, String umlPath, boolean simplifiedVersion)
	{
		return convertToTemporalUML(refmodel, umlPath, options, simplifiedVersion);
	}
	
	public static Resource includeTemporalStructure(org.eclipse.uml2.uml.Package umlRoot, String umlPath, boolean simplifiedVersion)
	{	
		if(!simplifiedVersion){
			tgenerator = new CompleteReificator(umlRoot, utransformer.getConverter().ufactory, utransformer.getConverter().umap);		
		}else{
			tgenerator = new SimplifiedReificator(umlRoot, utransformer.getConverter().ufactory, utransformer.getConverter().umap);
		}
		tgenerator.run();
		log += tgenerator.getLog();	
		
		generateOCLFile(umlPath);
		
		return OntoUML2UMLUtil.saveUML(umlPath,umlRoot);		
	}
	
	public static Resource convertToTemporalUML (RefOntoUML.Package refmodel, String umlPath, OntoUML2UMLOption opt, boolean simplifiedVersion)
	{
		Resource umlResource = convertToUML(refmodel, umlPath, opt);
		umlRoot = (org.eclipse.uml2.uml.Package)umlResource.getContents().get(0);		
		if(!simplifiedVersion){
			tgenerator = new CompleteReificator(umlRoot, utransformer.getConverter().ufactory, utransformer.getConverter().umap);
		}else{
			tgenerator = new SimplifiedReificator(umlRoot, utransformer.getConverter().ufactory, utransformer.getConverter().umap);
		}
		tgenerator.run();		
		log += tgenerator.getLog();	
		
		generateOCLFile(umlPath);
		
		umlResource = OntoUML2UMLUtil.saveUML(umlPath,umlRoot);	
		
		return umlResource;
	}
	
	public static Resource convertToTemporalUML(OntoUMLParser refparser, String umlPath, OntoUML2UMLOption opt, boolean simplifiedVersion)
	{
		Resource umlResource = convertToUML(refparser, umlPath, opt);
		umlRoot = (org.eclipse.uml2.uml.Package)umlResource.getContents().get(0);
		if(!simplifiedVersion){
			tgenerator = new CompleteReificator(umlRoot, utransformer.getConverter().ufactory, utransformer.getConverter().umap);
		}else{
			tgenerator = new SimplifiedReificator(umlRoot, utransformer.getConverter().ufactory, utransformer.getConverter().umap);
		}
		tgenerator.run();		
		log += tgenerator.getLog();	

		generateOCLFile(umlPath);
		
		umlResource = OntoUML2UMLUtil.saveUML(umlPath,umlRoot);		
		return umlResource;   		
	}
	
	private static void generateOCLFile(String umlPath)
	{
		File oclFile = FileUtil.createFile(umlPath.replace(".uml", ".ocl"));
		int lastIndex = umlPath.lastIndexOf(File.separator)+1;
		if(lastIndex<=0) lastIndex = umlPath.lastIndexOf("/")+1;
		String fileName = umlPath.substring(lastIndex);
		String header = new String("import '"+fileName+"'\n\n package _'"+umlRoot.getName()+"'\n\n");
		String footer = new String("endpackage");
		try {
			FileUtil.writeToFile(header+tgenerator.getConstraints()+footer, oclFile.getAbsolutePath());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//******************************
	// Getters
	//******************************
	
	public static HashMap <RefOntoUML.Element,org.eclipse.uml2.uml.Element> getStandardMap ()
	{
		return utransformer.getConverter().getMap();
	}
	
	public static HashMap<RefOntoUML.Element, ArrayList<org.eclipse.uml2.uml.Element>> getTemporalMap()
	{
		if (tgenerator !=null) return tgenerator.getMap();
		else return null;
	}
	
	public static String getLog ()
	{
		return log;
	}	
	
	public static org.eclipse.uml2.uml.Package getRoot() 
	{ 
		return umlRoot; 
	}
	
	public static String getUmlPath() { 
		return umlPath; 
	}
}
