package test;

import com.dnw.json.L;
import com.dnw.json.M;

public class TestIterable {

	public static final void main(String[] args) {
		L l = L.l().a(M.m().a("query", "merge (:Object {name:{name}}) on create set refs={refs}"))
				.a(L.l().a("some text", L.l().a(2, 3, 5)));
		System.out.println(l.vl(1).vl(1).v(1)); // output: 3
		M m = M.m().a("query", "merge (:Object {name:{name}}) on create set refs={refs}")
				.a("params", M.m().a("name", "new-object").a("refs", L.l().a(2, 3, 5)));
		System.out.println(m.vm("params").vl("refs").v(1)); // output: 3
	}
}
