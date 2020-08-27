package me.bytebeats.polyglot.util

import java.io.Reader
import java.io.StringReader

/**
 * @author bytebeats
 * @email <happychinapc@gmail.com>
 * @github https://github.com/bytebeats
 * @created on 2020/8/27 16:19
 * @version 1.0
 * @description GlotJsUtils offers some js functions.
 */

class GlotJsUtils {
    companion object {
        const val JS_BAIDU = """function a(r, o) {
          for (var t = 0; t < o.length - 2; t += 3) {
              var a = o.charAt(t + 2);
              a = a >= "a" ? a.charCodeAt(0) - 87 : Number(a),
              a = "+" === o.charAt(t + 1) ? r >>> a: r << a,
              r = "+" === o.charAt(t) ? r + a & 4294967295 : r ^ a
          }
          return r
      }
      var C = null;
      var token = function(r, _gtk) {
          var o = r.length;
          o > 30 && (r = "" + r.substr(0, 10) + r.substr(Math.floor(o / 2) - 5, 10) + r.substring(r.length, r.length - 10));
          var t = void 0,
          t = null !== C ? C: (C = _gtk || "") || "";
          for (var e = t.split("."), h = Number(e[0]) || 0, i = Number(e[1]) || 0, d = [], f = 0, g = 0; g < r.length; g++) {
              var m = r.charCodeAt(g);
              128 > m ? d[f++] = m: (2048 > m ? d[f++] = m >> 6 | 192 : (55296 === (64512 & m) && g + 1 < r.length && 56320 === (64512 & r.charCodeAt(g + 1)) ? (m = 65536 + ((1023 & m) << 10) + (1023 & r.charCodeAt(++g)), d[f++] = m >> 18 | 240, d[f++] = m >> 12 & 63 | 128) : d[f++] = m >> 12 | 224, d[f++] = m >> 6 & 63 | 128), d[f++] = 63 & m | 128)
          }
          for (var S = h,
          u = "+-a^+6",
          l = "+-3^+b+-f",
          s = 0; s < d.length; s++) S += d[s],
          S = a(S, u);
     
          return S = a(S, l),
          S ^= i,
          0 > S && (S = (2147483647 & S) + 2147483648),
          S %= 1e6,
          S.toString() + "." + (S ^ h)
      }"""

        const val JS_GOOGLE = """function token(a) {
          var k = "";
          var b = 406644;
          var b1 = 3293161072;
     
          var jd = ".";
          var sb = "+-a^+6";
          var Zb = "+-3^+b+-f";
     
          for (var e = [], f = 0, g = 0; g < a.length; g++) {
              var m = a.charCodeAt(g);
              128 > m ? e[f++] = m: (2048 > m ? e[f++] = m >> 6 | 192 : (55296 == (m & 64512) && g + 1 < a.length && 56320 == (a.charCodeAt(g + 1) & 64512) ? (m = 65536 + ((m & 1023) << 10) + (a.charCodeAt(++g) & 1023), e[f++] = m >> 18 | 240, e[f++] = m >> 12 & 63 | 128) : e[f++] = m >> 12 | 224, e[f++] = m >> 6 & 63 | 128), e[f++] = m & 63 | 128)
          }
          a = b;
          for (f = 0; f < e.length; f++) a += e[f],
          a = RL(a, sb);
          a = RL(a, Zb);
          a ^= b1 || 0;
          0 > a && (a = (a & 2147483647) + 2147483648);
          a %= 1E6;
          return a.toString() + jd + (a ^ b)
      };
     
      function RL(a, b) {
          var t = "a";
          var Yb = "+";
          for (var c = 0; c < b.length - 2; c += 3) {
              var d = b.charAt(c + 2),
              d = d >= t ? d.charCodeAt(0) - 87 : Number(d),
              d = b.charAt(c + 1) == Yb ? a >>> d: a << d;
              a = b.charAt(c) == Yb ? a + d & 4294967295 : a ^ d
          }
          return a
      }"""
        const val JS_SOUGO = """function token() {
	    return tk = t() + t() + t() + t() + t() + t() + t() + t()
        };
        function t() {
            return Math.floor(65536  (1 + Math.random())).toString(16).substring(1)
        }"""

        fun getReader(jsText: String): Reader {
            return StringReader(jsText)
        }
    }
}