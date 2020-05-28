package ml01;

public class Sample {
	/**
	 * 色泽（二进制表示，1表示选中对应的选项）
	 */
	int col;
	/**
	 * 根蒂（二进制表示，1表示选中对应的选项）
	 */
	int root;
	/**
	 * 敲声（二进制表示，1表示选中对应的选项）
	 */
	int sound;
	public Sample(int c, int r, int s) {
		col = c;
		root = r;
		sound = s;
	}
	public int getCol() {
		return col;
	}
	public void setCol(int col) {
		this.col = col;
	}
	public int getRoot() {
		return root;
	}
	public void setRoot(int root) {
		this.root = root;
	}
	public int getSound() {
		return sound;
	}
	public void setSound(int sound) {
		this.sound = sound;
	}
	
}
