package OrdinalStatisticTree;


public class RB_Node implements Cloneable {
	private MyCOLOR color;
	private double key;
	private RB_Node left;
	private RB_Node right;
	private RB_Node parent;
	int size;
	
	public MyCOLOR getColor() {
		return this.color;
	}

	public double getKey() {
		return this.key;
	}

	public RB_Node getLeft() {
		return this.left;
	}

	public RB_Node getRight() {
		return this.right;
	}

	public RB_Node getParent() {
		return this.parent;
	}

	public RB_Node getGrandparent() {
		try {
			return this.parent.parent;
		} catch (Exception e) {
			return null;
		}

	}

	public void setColorRed() {
		this.color = MyCOLOR.RED;
	}

	public void setColorBlack() {
		this.color = MyCOLOR.BLACK;
	}

	public void setLeft(RB_Node newNode) {
		this.left = newNode;
	}

	public void setRight(RB_Node newNode) {
		this.right = newNode;
	}

	public void setParent(RB_Node newParent) {
		this.parent = newParent;
	}

	public RB_Node() {
		size=1;
		setColorRed();
		this.left = this.right = this.parent = null;
	}

	public RB_Node(RB_Node leaf) {
		this();
		this.left = this.right = leaf;
	}

	public RB_Node(double key, RB_Node leaf, RB_Node parent) {
		this(leaf);
		this.key = key;
		this.parent = parent;
	}

	public RB_Node(double key, RB_Node leaf) {
		this(key, leaf, null);
	}

	

//	private void correctionParent(Persistent_RB_Tree<T> tree) {
//		if (this.left != null && this.left != tree.getLeaf()) {
//			this.left.parent = this;
//		}
//		if (this.right != null && this.right != tree.getLeaf()) {
//			this.right.parent = this;
//		}
//	}

	public void left_rotate(OrdinalStatisticRedBlackBTree tree) {
		RB_Node y = this.right;
		
		this.right = y.left;

		if (y.left != tree.getLeaf()) {
			y.left.parent = this;
		}
		y.parent = this.parent;
		if (this.parent == null) {
			tree.setRoot(y);
		} else if (this == this.parent.left) {
			this.parent.left = y;
		} else {
			this.parent.right = y;
		}
		y.left = this;
		this.parent = y;
		y.size=this.size;
		this.size=this.left.size + this.right.size+1;
	}

	

	public void right_rotate(OrdinalStatisticRedBlackBTree tree) {
		RB_Node y = this.left;
		this.left = y.right;

		if (this.left != tree.getLeaf()) {
			this.left.parent = this;
		}
		y.parent = this.parent;
		if (y.parent == null) {
			tree.setRoot(y);
		} else if (this == this.parent.left) {
			this.parent.left = y;
		} else {
			this.parent.right = y;
		}
		y.right = this;
		this.parent = y;
		
		y.size=this.size;
		this.size=this.left.size + this.right.size+1;
	}

	

	public void print() {
		System.out.println(this.key + " " + this.color + " " + this.left + " " + this.right);
	}

//	public int numberOfNodeOnLeftSide(OrdinalStatisticRedBlackBTree tree) {
//		
//		if (this == tree.getLeaf() || this==null) {
//			return 0;
//		}
//		int number = recursionNumberOfNodes(this.getLeft(), tree.leaf);
//		RB_Node current=this;
//		while(current.getParent()!=null &&  current.getParent()!=tree.leaf) {
//			if (current.parent.getRight()==current) {
//				number+=recursionNumberOfNodes(current.parent.left, tree.leaf)+1;
//			}
//			current=current.getParent();
//			
//			
//		}
//		return number;
//	}

//	private int recursionNumberOfNodes(RB_Node current, RB_Node leaf) {
//		
//		if (current == leaf ) {
//			return 0;
//		}
//		return 1+recursionNumberOfNodes(current.getLeft(), leaf)+recursionNumberOfNodes(current.getRight(), leaf);
//	}


	
}
