package ads.poo2.lab2.binaryTrees;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * A class for genealogy 
 */
public class GenealogyTree extends BinaryTree<String> {

	private static final Logger LOGGER = Logger.getLogger(GenealogyTree.class.getName());

	/**
	 * Build an orphan node of name 'name'
	 * (no father/mother genealogy)
	 */
	public GenealogyTree(String name) {
		super(name);
	}
	
	/**
	 * Build a genealogy node of name 'name'
	 * with 'father' the genealogy of the father
	 * and 'mother' the genealogy of the mother
	 */
	public GenealogyTree(String name,
						 GenealogyTree father,
						 GenealogyTree mother) {
		super(name,father,mother);
	}
	
	//////////////////////
	// ancestors
	
	/**
	 * Return the list of all ancestors from
	 * this genealogy at level 'level'
	 */
	public List<String> ancestors(int level) {
		List<String> ancestors = new ArrayList<>();
		ancestors(this,0,level,ancestors);
		return ancestors;
	}
	
	private void ancestors(BinaryTreeInterface<String> t, int currentLevel, int targetLevel, List<String> ancestors) {
		if ( t != null ) {
			if ( currentLevel == targetLevel )
				ancestors.add(t.getData());
			else {
				ancestors(t.left(),currentLevel+1,targetLevel,ancestors);
				ancestors(t.right(),currentLevel+1,targetLevel,ancestors);
			}
		}
	}
	
	//////////////////////
	// maleAncestors
	
	/**
	 * Return the list of all male ancestors from
	 * the this genealogy at level 'level'
	 */	
	public List<String> maleAncestors(int level) {
		List<String> ancestors = new ArrayList<>();
		maleAncestors(this,0,level,ancestors);
		return ancestors;
	}
	
	private void maleAncestors(BinaryTreeInterface<String> t, int currentLevel, int targetLevel, List<String> ancestors) {
		if ( t != null ) {
			if ( currentLevel == targetLevel - 1 ) {
				if ( t.left() != null )
					ancestors.add(t.left().getData());
			}
			else {
				maleAncestors(t.left(),currentLevel+1,targetLevel,ancestors);
				maleAncestors(t.right(),currentLevel+1,targetLevel,ancestors);
			}
		}
	}
	
	//////////////////////
	// displayGenerations
	
	/**
	 * Print all the ancestors line by lie, each line
	 * being a generation (i.e. a level in the tree)
	 */
	public void displayGenerations()  {
		Queue<BinaryTreeInterface<String>> q = new LinkedList<>();
		q.add(this);
		while ( ! q.isEmpty() ) {
			int size = q.size();
			for ( int i = 0; i < size; i++ ) {
				BinaryTreeInterface<String> t = q.poll();
				System.out.print(t.getData() + " ");
				LOGGER.log(Level.INFO, "t.getData() = {0}", t.getData());
				if ( t.left() != null )
					q.add(t.left());
				if ( t.right() != null )
					q.add(t.right());
			}
			System.out.println();
			LOGGER.log(Level.INFO, "q = {0}", q);
		}
	}
	
    ////////////////////////////////////
    
	/**
	 * Return a genealogy tree whose linear form
	 * is given as the string 'inputstring'
	 */
    public static GenealogyTree read(String inputString) {
    	try(Scanner input = new Scanner(inputString)) {
			return read(input);
		}
    }
    
    private static GenealogyTree read(Scanner input) {
    	if ( ! input.hasNext() )
    		return null;
    	String s = input.next();
    	if ( s.equals("$") )
    		return null;
    	if ( s.endsWith("$") )
    		return new GenealogyTree(s.substring(0,s.length()-1));
    	return new GenealogyTree(s,read(input),read(input));
    }

	//<enfant maman papa> <maman grandmere grandpere> <grandmere $ arrGP > <grandpere $ $><arrGP aaGM aaGP>
	private static GenealogyTree readOO(String input) throws MalformedStringException {
		Pattern pattern = Pattern.compile("<(.*?)>");
		Matcher matcher = pattern.matcher(input);

		HashMap<String, GenealogyTree> hashmap = new HashMap<>();

		GenealogyTree root = null;
		while (matcher.find()) {
			String[] elements = matcher.group(1).split(" ");
			String child = elements[0];
			String mother = elements[1];
			String father = elements[2];
			GenealogyTree childNode;
			if (hashmap.isEmpty()) {
				root = new GenealogyTree(child);
				childNode = root;
				hashmap.put(child, root);
			}
			else {
				childNode = hashmap.get(child);
				if (childNode == null)
					throw new MalformedStringException(child);
			}
			GenealogyTree motherNode = createGenealogyTree(hashmap, mother);
			GenealogyTree fatherNode = createGenealogyTree(hashmap, father);
			childNode.setLeftBT(motherNode);
			childNode.setRightBT(fatherNode);
		}
		return root;
	}

	private static final String UNKNOWN = "$";
	private static GenealogyTree createGenealogyTree(Map<String, GenealogyTree> hashmap, String nodeName) {
		if (hashmap.containsKey(nodeName))
			return hashmap.get(nodeName);
		if (nodeName.equals(UNKNOWN))
			return null;
		GenealogyTree node = new GenealogyTree(nodeName);
		hashmap.put(nodeName,node);
		return node;
	}

	/**
     * A main for quick testing
     */
	public static void main(String[] args) {
		LOGGER.setLevel(Level.WARNING);
		try {
			GenealogyTree myTree = readOO("<enfant maman papa> <papa $ gmpapa > <maman grandmere grandpere> <grandmere $ arrGP > <grandpere $ $> <arrGP aaGM aaGP> <aaGM aaaGM $>");
			System.out.println(myTree);
			myTree.displayGenerations();
		} catch (MalformedStringException e) {
			System.out.println("erreur " + e);
		}
		GenealogyTree g = read("Edward David Carl $ Barbara Anthony$ Anna$ $ Dorothy Craig Bruce Allan$ Amanda$ $ Carol Brian Andrew$ Ann$ Brenda Albert$ Alice$");
		System.out.println(g);

		g.displayGenerations();

		System.out.println();
		
		System.out.print("Ancestors at generation 1 (parents): ");
		for ( String s : g.ancestors(1) ) {
			System.out.print(s + " ");
		}
		System.out.println();
		
		System.out.print("Ancestors at generation 2 (grand-parents): ");
		for ( String s : g.ancestors(2) ) {
			System.out.print(s + " ");
		}
		System.out.println();
		
		System.out.print("Ancestors at generation 3: ");
		for ( String s : g.ancestors(3) ) {
			System.out.print(s + " ");
		}
		System.out.println();
		
		System.out.print("Male ancestors at generation 1 (father): ");
		for ( String s : g.maleAncestors(1) )
			System.out.print(s + " ");
		System.out.println();
		
		System.out.print("Male ancestors at generation 2 (grand-fathers): ");
		for ( String s : g.maleAncestors(2) )
			System.out.print(s + " ");
		System.out.println();
		
		System.out.print("Male ancestors at generation 3: ");
		for ( String s : g.maleAncestors(3) )
			System.out.print(s + " ");
		System.out.println();
		
		System.out.print("Ancestors at generation 10 (empty): ");
		for ( String s : g.ancestors(10) )
			System.out.print(s + " ");
		System.out.println();
		
		System.out.println("All generations:");
			g.displayGenerations();
			System.out.println("Unexpected Exception ! ");

		System.out.println();
	}
	// expected output
	//
	// Edward
	// |______ Dorothy
	// |       |_______ Carol
	// |       |        |_____ Brenda
	// |       |        |      |______ Alice
	// |       |        |      |
	// |       |        |      |______ Albert
	// |       |        |
	// |       |        |_____ Brian
	// |       |               |_____ Ann
	// |       |               |
	// |       |               |_____ Andrew
	// |       |
	// |       |_______ Craig
	// |                |_____ 
	// |                |
	// |                |_____ Bruce
	// |                       |_____ Amanda
	// |                       |
	// |                       |_____ Allan
	// |
	// |______ David
	//         |_____ 
	//         |
	//        |_____ Carl
	//                |____ Barbara
	//                |     |_______ Anna
	//                |     |
	//                |     |_______ Anthony
	//                |
	//                |____ 
	// 
	// Ancestors at generation 1 (parents): David Dorothy 
	// Ancestors at generation 2 (grand-parents): Carl Craig Carol 
	// Ancestors at generation 3: Barbara Bruce Brian Brenda 
	// Male ancestors at generation 1 (father): David 
	// Male ancestors at generation 2 (grand-fathers): Carl Craig 
	// Male ancestors at generation 3: Bruce Brian 
	// Ancestors at generation 10 (empty): 
	// All generations:
	// Edward 
	// David Dorothy 
	// Carl Craig Carol 
	// Barbara Bruce Brian Brenda 
	// Anthony Anna Allan Amanda Andrew Ann Albert Alice 
}
