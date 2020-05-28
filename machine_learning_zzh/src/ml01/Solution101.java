package ml01;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;


/**
 * 机器学习-周志华 习题1.1
 * @author wangjiuliang
 *
 */
public class Solution101 {
	/**
	 * 色泽选项个数
	 */
	private int cSize;
	/**
	 * 根蒂选项个数
	 */
	private int rSize;
	/**
	 * 敲声选项个数
	 */
	private int sSize;
	/**
	 * 正例个数
	 */
	private int pSize;
	/**
	 * 反例个数
	 */
	private int nSize;
	/**
	 * 正例
	 */
	private List<Sample> positiveSamples;
	/**
	 * 反例
	 */
	private List<Sample> negativeSamples;
	private Scanner in = new Scanner(System.in);
	
	/**
	 * 
	 * @param cz 色泽选项个数
	 * @param rz 根蒂选项个数
	 * @param sz 敲声选项个数
	 */
	public Solution101() {
		System.out.println("请输入色泽、根蒂和敲声各自的选项个数：");
		cSize = in.nextInt();
		rSize = in.nextInt();
		sSize = in.nextInt();
		init();
	}
	
	private void init() {	
		/*输入正例*/
		System.out.println("输入正例条数：");
		this.pSize = in.nextInt();
		System.out.println("输入正例：\n(111表示*，100表示选择第1个选项)");
		this.positiveSamples = new ArrayList<>();
		for (int i = 0; i < this.pSize; i++) {
			int col = in.nextInt();
			int root = in.nextInt();
			int sound = in.nextInt();
			Sample sp = new Sample(col, root, sound);
			this.positiveSamples.add(sp);
		}
		
		/*输入反例*/
		System.out.println("输入反例条数：");
		this.nSize = in.nextInt();
		System.out.println("输入反例：\n(111表示*，100表示选择第1个选项)");
		this.negativeSamples = new ArrayList<>();
		for (int i = 0; i < this.nSize; i++) {
			int col = in.nextInt();
			int root = in.nextInt();
			int sound = in.nextInt();
			Sample sp = new Sample(col, root, sound);
			this.negativeSamples.add(sp);
		}
		in.close();
	}

	/**
	 * 生成泛化后的记录
	 * @param origin
	 * @param n
	 * @return
	 */
	public static List<Integer> generalize(int origin, int n) {
		List<Integer> res = new ArrayList<>();
		int mask = (1 << n) - 1;
		while (mask != 0) {
			if ((origin | mask) == mask) { //A | B = A, 则A属于B
				res.add(mask);
			}
			mask--;
		}
		return res;
	}
	
	/**
	 * list中数字以二进制形式打印
	 * @param list
	 */
	public static void arr2str(Set<Integer> list) {
		for (int l : list) {
			System.out.println(Integer.toBinaryString(l));
		}
		System.out.println();
	}
	
	/**
	 * 计算版本空间
	 * @return
	 */
	public Set<Integer> versionSpace() {
		Set<Integer> vs = new HashSet<>();
		
		/*由正例泛化记录*/
		for (Sample ps : this.positiveSamples) {
			List<Integer> cols = generalize(ps.getCol(), this.cSize);
			List<Integer> roots = generalize(ps.getRoot(), this.rSize);
			List<Integer> sounds = generalize(ps.getSound(), this.sSize);
			int sample;
			for (int col : cols) {
				for (int root : roots) {
					for (int sound : sounds) {
						sample = (col << (this.rSize + this.sSize)) + (root << this.sSize) + sound;
						vs.add(sample);
					}
				}
			}
		}
		
		/*由反例删除记录*/
		for (Sample ns : this.negativeSamples) {
			List<Integer> cols = generalize(ns.getCol(), this.cSize);
			List<Integer> roots = generalize(ns.getRoot(), this.rSize);
			List<Integer> sounds = generalize(ns.getSound(), this.sSize);
			int sample;
			for (int col : cols) {
				for (int root : roots) {
					for (int sound : sounds) {
						sample = (col << (this.rSize + this.sSize)) + (root << this.sSize) + sound;
						vs.remove(sample);
					}
				}
			}
		}
		
		return vs;
	}
	
	/**
	 * 打印版本空间
	 */
	public void printVersionSpace() {
		Set<Integer> vs = this.versionSpace();
		arr2str(vs);
		this.parseVersionSpace(vs);
	}
	
	private void parseVersionSpace(Set<Integer> vs) {
		String[] attributes = {"色泽", "根蒂", "敲声"};
		String[][] choices = {{"乌黑", "青绿", "*"}, {"稍蜷", "蜷缩", "*"}, {"沉闷", "浊响", "*"}};
		
		for (int rec : vs) {
			int c = (rec & 0b110000) >> 4;
			int r = (rec & 0b001100) >> 2;
			int s = rec & 0b000011;
			System.out.println("(" + attributes[0] + " = " + choices[0][c-1] + "; "
					+ attributes[1] + " = " + choices[1][r-1] + "; "
					+ attributes[2] + " = " + choices[2][s-1] + ")");
		}
		System.out.println();
		
	}
	
	public static void main(String[] args) {
		Solution101 solution = new Solution101();
		solution.printVersionSpace();
	}

}
