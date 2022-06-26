package OrdinalStatisticTree;

public class OrdinalStatisticRedBlackBTree {
	private final double accuracy = 0.00000000001;
	private RB_Node root;
	RB_Node leaf;

	public RB_Node getRoot() {
		return this.root;
	}

	public RB_Node getLeaf() {
		return this.leaf;
	}

	public OrdinalStatisticRedBlackBTree() {
		this.root = null;
		this.leaf = new RB_Node();
		this.leaf.size = 0;
		RB_Node last = new RB_Node();
		last.setColorBlack();
		this.leaf.setLeft(last);
		this.leaf.setRight(last);

		this.leaf.setColorBlack();
	}

	public void setRoot(RB_Node root) {
		this.root = root;
	}

	public boolean insert(double key) {
		if (this.root == null) {
			this.root = new RB_Node(key, this.leaf, this.leaf);
			this.root.setColorBlack();
			return true;
		}

		RB_Node current = this.root;
		while (current != this.leaf) {
			if (Math.abs(current.getKey() - (key)) < accuracy) {
				return false;
			}
			if (current.getKey() - (key) < 0) {
				if (current.getRight() != this.leaf) {
					current = current.getRight();
					continue;
				}
				current.setRight(new RB_Node(key, this.leaf, current));
				fixTree(current.getRight());
				return true;
			} else {
				if (current.getLeft() != this.leaf) {
					current = current.getLeft();
					continue;
				}
				current.setLeft(new RB_Node(key, this.leaf, current));
				fixTree(current.getLeft());
				return true;
			}
		}

		return false;
	}

	private void fixTree(RB_Node current) throws NullPointerException {
		returningToRootWithIncrementingSizeOfParents(current.getParent());
		RB_Node y;
		while (current.getParent().getColor() == MyCOLOR.RED) {

			if (current.getParent() == current.getGrandparent().getLeft()) {
				y = current.getGrandparent().getRight();
				if (y.getColor() == MyCOLOR.RED) {
					current.getParent().setColorBlack();
					y.setColorBlack();
					current.getGrandparent().setColorRed();
					current = current.getGrandparent();

				} else if (current == current.getParent().getRight()) {
					current = current.getParent();
					current.left_rotate(this);
				} else {
					current.getParent().setColorBlack();
					current.getGrandparent().setColorRed();
					current.getGrandparent().right_rotate(this);

				}
			} else {
				y = current.getGrandparent().getLeft();
				if (y.getColor() == MyCOLOR.RED) {
					current.getParent().setColorBlack();
					y.setColorBlack();
					current.getGrandparent().setColorRed();
					current = current.getGrandparent();

				} else if (current == current.getParent().getLeft()) {
					current = current.getParent();
					current.right_rotate(this);
				} else {
					current.getParent().setColorBlack();
					current.getGrandparent().setColorRed();
					current.getGrandparent().left_rotate(this);

				}
			}
		}
		while (this.root.getParent() != this.leaf) {
			this.root = this.root.getParent();
		}
		this.root.setColorBlack();

	}

	public void print() {
		if (this.root == null) {
			return;
		}
		printNodeWithRecursion(this.root, 0);
	}

	protected void printNodeWithRecursion(RB_Node current, int n) {

		if (current == this.leaf) {
			for (int i = 0; i < n; i++) {
				System.out.print("\t");
			}
			System.out.println("_");
			return;
		}
		printNodeWithRecursion(current.getRight(), n + 1);

		for (int i = 0; i < n; i++) {
			System.out.print("\t");
		}
		System.out.println(current.getKey() + " " + current.getColor() + " " + current.size);
		printNodeWithRecursion(current.getLeft(), n + 1);
	}

	private void transplant(RB_Node node1, RB_Node node2) {
		if (node1.getParent() == this.leaf) {
			this.root = node2;
		} else if (node1 == node1.getParent().getLeft()) {
			node1.getParent().setLeft(node2);
		} else {
			node1.getParent().setRight(node2);
		}
		node2.setParent(node1.getParent());
	}

	public RB_Node search(double key) {
		RB_Node current = this.root;
		while (current != this.leaf) {

			if (current.getKey() - (key) < accuracy) {
				return current;
			} else if (current.getKey() - (key) > 0) {
				current = current.getLeft();
			} else {
				current = current.getRight();
			}
		}
		return this.leaf;
	}

	public boolean delete(double key) {
		RB_Node z = this.search(key);
		if (z == this.leaf) {
			System.out.println("There isn't the element");
			return false;
		}

		RB_Node y = z;
		RB_Node x;
		MyCOLOR y_color = y.getColor();

		if (z.getLeft() == this.leaf) {
			x = z.getRight();
			transplant(z, z.getRight());
		} else if (z.getRight() == this.leaf) {
			x = z.getLeft();
			transplant(z, z.getLeft());
		} else {
			y = min_son(z.getRight());
			y_color = y.getColor();
			x = y.getRight();
			if (y.getParent() == z) {
				x.setParent(y);
			} else {
				transplant(y, y.getRight());
				y.setRight(z.getRight());
				y.getRight().setParent(y);
			}
			transplant(z, y);
			y.setLeft(z.getLeft());
			y.getLeft().setParent(y);
			if (z.getColor() == MyCOLOR.RED) {
				y.setColorRed();
			} else {
				y.setColorBlack();
			}
		}
		if (y_color == MyCOLOR.BLACK) {
			deleteFixUp(x);
		}
		while (this.root.getParent() != this.leaf) {
			this.root = this.root.getParent();
		}
		correctionSize(this.root);
		return true;
	}

	private void deleteFixUp(RB_Node x) {
		RB_Node w;
		while (x != this.root && (x.getColor() == MyCOLOR.BLACK)) {
			if (x == x.getParent().getLeft()) {

				w = x.getParent().getRight();
				if (w.getColor() == MyCOLOR.RED) {
					w.setColorBlack();
					x.getParent().setColorRed();
					x.getParent().left_rotate(this);
					w = x.getParent().getRight();
				}
				if (w.getLeft().getColor() == MyCOLOR.BLACK && w.getRight().getColor() == MyCOLOR.BLACK) {
					w.setColorRed();
					x = x.getParent();
				} else if (w.getRight().getColor() == MyCOLOR.BLACK) {
					w.getLeft().setColorBlack();
					w.setColorRed();
					w.right_rotate(this);
					w = x.getParent().getRight();
				} else {
					if (x.getParent().getColor() == MyCOLOR.RED) {
						w.setColorRed();
					} else {
						w.setColorBlack();
					}
					x.getParent().setColorBlack();
					w.getRight().setColorBlack();
					x.getParent().left_rotate(this);
					x = this.root;
				}
			} else {
				w = x.getParent().getLeft();
				if (w.getColor() == MyCOLOR.RED) {
					w.setColorBlack();
					x.getParent().setColorRed();
					x.getParent().right_rotate(this);
					w = x.getParent().getLeft();
				}
				if (w.getLeft().getColor() == MyCOLOR.BLACK && w.getRight().getColor() == MyCOLOR.BLACK) {
					w.setColorRed();
					x = x.getParent();
				} else if (w.getLeft().getColor() == MyCOLOR.BLACK) {
					w.getRight().setColorBlack();
					w.setColorRed();
					w.left_rotate(this);
					w = x.getParent().getLeft();
				} else {
					if (x.getParent().getColor() == MyCOLOR.RED) {
						w.setColorRed();
					} else {
						w.setColorBlack();
					}
					x.getParent().setColorBlack();
					w.getLeft().setColorBlack();
					x.getParent().right_rotate(this);
					x = this.root;
				}
			}
		}
		// this.root.setColorBlack();
		x.setColorBlack();

	}

	private RB_Node min_son(RB_Node rootOfSearch) {
		RB_Node current = rootOfSearch;
		while (current.getLeft() != this.leaf) {
			current = current.getLeft();
		}
		return current;
	}

	private void returningToRootWithIncrementingSizeOfParents(RB_Node current) {
		if (current == null || current == this.leaf) {
			return;
		}
		current.size++;
		returningToRootWithIncrementingSizeOfParents(current.getParent());
	}

	private int correctionSize(RB_Node current) {
		if (current == leaf) {
			return 0;
		}
		current.size = correctionSize(current.getLeft()) + correctionSize(current.getRight()) + 1;
		return current.size;
	}

	public RB_Node getKeyByIndex(int index) {
		if (this.root == null || this.root == this.leaf) {
			return null;
		}
		if (index <= 0 || index > this.root.size) {
			return null;
		} else {
			return getKeyByIndexRecursion(this.root, index);
		}
	}

	private RB_Node getKeyByIndexRecursion(RB_Node current, int index) {
		if (current == leaf) {
			return null;
		}
		int l = current.getLeft().size + 1;
		if (index == l) {
			return current;
		} else if (index < l) {
			return getKeyByIndexRecursion(current.getLeft(), index);
		}
		return getKeyByIndexRecursion(current.getRight(), index - l);
	}
}

//вузол червоний або чорний
//корінь тільки чорний
//кожен лист чорний
//якщо вузол червоний то сини чорні
//у кожного вузла праве і ліве піддерево мають однак. к-ть чорних вузлів(листи не рахувати)