# hangul-utils

A Clojure library for manipulating Korean characters and alphabets.

## Usage

This library represents a deconstructed Korean syllable as a vector of letters (or jamo).

```clojure
(deconstruct \안)
;; => [\ㅇ \ㅏ \ㄴ]

(deconstruct-str "안녕하세요")
;; => [[\ㅇ \ㅏ \ㄴ] [\ㄴ \ㅕ \ㅇ] [\ㅎ \ㅏ] [\ㅅ \ㅔ] [\ㅇ \ㅛ]]

(construct [\ㅎ \ㅏ])
;; => \하

(construct-str [[\ㅋ \ㅡ \ㄹ] [\ㄹ \ㅗ] [\ㅈ \ㅕ] [\space] [\ㅈ \ㅐ] [\ㅁ \ㅣ \ㅆ] [\ㄴ \ㅔ] [\ㅇ \ㅛ]])
;; => "클로져 재밌네요"
```

You can also transform strings end-to-end:

```clojure
(alphabetize "오늘부터..!")
;; => "ㅇㅗㄴㅡㄹㅂㅜㅌㅓ..!"

(syllabize "ㄹㅣㅊㅣㅎㅣㅋㅣㄴㅣㅁ ㄱㅗㅁㅏㅂㅅㅡㅂㄴㅣㄷㅏ")
;; => "리치히키님 고맙습니다"
```

## Thanks

Thanks to kaniblu for the [hangul-utils](https://github.com/kaniblu/hangul-utils)

## License

Copyright © 2017 Sooheon Kim

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
