# hangul-utils

A Clojure library for manipulating Korean characters and alphabets.

The project is hosted on Clojars:

[![Clojars Project](https://img.shields.io/clojars/v/hangul-utils.svg)](https://clojars.org/hangul-utils)

## Usage
Hangul alphabet letters called jamo (자모) are grouped into syllabic blocks, forming a single unicode char. So when dealing with Korean text at the alphabet level, some way of deconstructing the syllabic blocks into a list of jamo, and then reconstructing them, is useful.

This library represents a deconstructed Korean syllable as a vector of letters (or jamo).

```clojure
(deconstruct \안)
;; => [\ㅇ \ㅏ \ㄴ]

(deconstruct-str "안녕하세요! ㅎㅎ")
;; => [[\ㅇ \ㅏ \ㄴ] [\ㄴ \ㅕ \ㅇ] [\ㅎ \ㅏ] [\ㅅ \ㅔ] [\ㅇ \ㅛ] [\!] [\space] [\ㅎ] [\ㅎ]]

(construct [\ㅎ \ㅏ])
;; => \하

(construct-str [[\ㅋ \ㅡ \ㄹ] [\ㄹ \ㅗ] [\ㅈ \ㅕ] [\space] [\ㅈ \ㅐ] [\ㅁ \ㅣ \ㅆ] [\ㄴ \ㅔ] [\ㅇ \ㅛ]])
;; => "클로져 재밌네요"
```

You can also transform strings end-to-end. Since it is common to encounter Korean which is full of non-standard spellings and text-emoticons, the library is lenient about unrecognized syllables and preserves text such as 'ㅎㅎ \*^.^\*' in round trip transformations.

```clojure
(alphabetize "오늘부터..!")
;; => "ㅇㅗㄴㅡㄹㅂㅜㅌㅓ..!"

(syllabize "ㄹㅣㅊㅣㅎㅣㅋㅣㄴㅣㅁ ㄱㅗㅁㅏㅂㅅㅡㅂㄴㅣㄷㅏ")
;; => "리치히키님 고맙습니다"

(alphabetize "아ㅏㅏㅏㅏㅏ이고")
;; => "ㅇㅏㅏㅏㅏㅏㅏㅇㅣㄱㅗ"
```

Be aware that `alphabetize` is just a simple helper function to avoid having to call `(apply str ...)` too often. Since the jamo are represented in plain Clojure vectors, you can always do operations such as `(flatten (deconstruct-str "가나다라마"))` to get `'(\ㄱ \ㅏ \ㄴ \ㅏ \ㄷ \ㅏ \ㄹ \ㅏ \ㅁ \ㅏ)`.

## Thanks

Thanks to kaniblu for the Python [hangul-utils](https://github.com/kaniblu/hangul-utils) library, which inspired this.

## License

Copyright © 2017 Sooheon Kim

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
