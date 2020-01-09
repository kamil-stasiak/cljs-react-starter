
Start app
```
npx shadow-cljs watch app
```

nREPL
```
(shadow.cljs.devtools.api/nrepl-select :app)
(in-ns 'me.stasiak.starter.core)
```

```
(require 'clojure.repl)
(clojure.repl/dir
  me.stasiak.starter.core)
```
