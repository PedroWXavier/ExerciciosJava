# Exercícios Práticos para Java Sênior

> Roteiro progressivo de exercícios cobrindo os principais tópicos para evoluir de pleno para sênior em Java. Resolva sem IA primeiro — a dificuldade é o que constrói o aprendizado.

---

## Sumário

1. [Lambdas e Functional Interfaces](#1-lambdas-e-functional-interfaces)
2. [Streams Avançados](#2-streams-avançados)
3. [Concorrência](#3-concorrência)
4. [JVM e Performance](#4-jvm-e-performance)
5. [Spring por Dentro](#5-spring-por-dentro)
6. [JPA/Hibernate](#6-jpahibernate)
7. [Testes](#7-testes)
8. [Design e Arquitetura](#8-design-e-arquitetura)
9. [Como Tirar Máximo Proveito](#como-tirar-máximo-proveito)

---

## 1. Lambdas e Functional Interfaces

### Exercício 1.1 — Aquecimento

Dada uma `List<String>` com nomes, use streams + lambdas para:

- Filtrar nomes com mais de 4 letras
- Transformar para maiúsculo
- Ordenar alfabeticamente
- Juntar com vírgula

Tudo em uma única pipeline.

### Exercício 1.2 — Functional Interfaces customizadas

Crie uma interface funcional `Validator<T>` com método `boolean validate(T value)`.

Implemente um `ValidatorChain` que permita combinar validators com `and`, `or`, `negate` (similar ao `Predicate`).

Use para validar um objeto `User`:
- Nome não vazio
- Idade ≥ 18
- Email com `@`

### Exercício 1.3 — Function composition

Crie funções `Function<Integer, Integer>` para:

- Dobrar
- Somar 10
- Elevar ao quadrado

Componha-as em ordens diferentes usando `andThen` e `compose` e observe o resultado. Explique a diferença entre as duas.

### Exercício 1.4 — Method references

Reescreva todas as lambdas dos exercícios anteriores usando method references onde possível. Identifique quando não é possível e por quê.

---

## 2. Streams Avançados

### Exercício 2.1 — Collectors

Dada uma `List<Order>` (com `customerId`, `value`, `category`), usando apenas streams:

- Agrupe por categoria e some o valor total
- Encontre o cliente com maior gasto total
- Particione orders entre acima e abaixo da média
- Crie um `Map<String, List<Order>>` agrupado por cliente, ordenado pelo valor desc

### Exercício 2.2 — Collector customizado

Implemente seu próprio `Collector` que calcula média, mínimo, máximo e desvio padrão em uma única passagem (sem usar `summarizingDouble`).

### Exercício 2.3 — Streams vs loops

Pegue um código com loops aninhados e if/else complexos (pode ser do seu trabalho) e refatore para streams.

Depois faça o caminho contrário: pegue um stream complexo e transforme em loop. Reflita sobre legibilidade em cada caso.

---

## 3. Concorrência

### Exercício 3.1 — Race condition na prática

Crie um contador compartilhado e incremente-o de 10 threads, 1000 vezes cada. Veja o resultado errado.

Resolva de três formas:
- `synchronized`
- `AtomicInteger`
- `LongAdder`

Faça benchmark simples comparando.

### Exercício 3.2 — Producer-Consumer

Implemente o padrão clássico usando `BlockingQueue`.

Depois reimplemente do zero usando apenas `wait/notify` com `synchronized`. Sinta a dor para apreciar a abstração.

### Exercício 3.3 — CompletableFuture

Simule três chamadas a APIs externas (use `Thread.sleep` com tempos diferentes).

- Execute-as em paralelo
- Combine os resultados quando todas terminarem
- Tenha um timeout global de 2 segundos
- Trate exceções de cada uma individualmente

### Exercício 3.4 — Virtual Threads (Java 21)

Faça 10.000 requisições HTTP simuladas (com sleep) usando:

- Platform threads com pool fixo
- Virtual threads

Compare consumo de memória e tempo total. Entenda quando virtual threads brilham.

### Exercício 3.5 — Deadlock

Crie deliberadamente um deadlock entre duas threads que travam dois recursos em ordem inversa.

Use `jstack` ou ferramenta de profiling para detectá-lo. Depois corrija com lock ordering.

---

## 4. JVM e Performance

### Exercício 4.1 — Memory leak

Crie um cache em `HashMap` que cresce indefinidamente.

- Rode com `-Xmx128m` e cause `OutOfMemoryError`
- Gere heap dump com `-XX:+HeapDumpOnOutOfMemoryError`
- Abra no VisualVM ou Eclipse MAT e identifique o leak
- Corrija usando `WeakHashMap` ou cache com TTL

### Exercício 4.2 — JMH benchmark

Configure JMH no projeto e faça benchmark comparando:

- `ArrayList` vs `LinkedList` para inserção no meio
- `String` concat com `+` vs `StringBuilder` em loop
- `HashMap` vs `ConcurrentHashMap` vs `Collections.synchronizedMap` em acesso concorrente

### Exercício 4.3 — GC tuning

Crie uma aplicação que aloca muitos objetos de curta duração.

Rode com `-Xlog:gc*` usando G1 e ZGC. Compare pause times e throughput. Aprenda a ler GC logs.

---

## 5. Spring por Dentro

### Exercício 5.1 — IoC do zero

Implemente um mini container de injeção de dependências em ~100 linhas.

Suporte:
- `@Inject` (sua anotação)
- Singleton
- Descoberta por reflection em um pacote

Isso desmistifica o Spring.

### Exercício 5.2 — Proxies e o pitfall do self-invocation

Crie um service Spring com dois métodos, um chamando o outro, ambos com `@Transactional` com propagações diferentes.

Veja por que a transação interna é ignorada. Resolva com `AopContext.currentProxy()` ou injetando o próprio bean.

### Exercício 5.3 — Bean lifecycle

Crie um bean implementando todas as interfaces de ciclo de vida:

- `BeanNameAware`
- `InitializingBean`
- `DisposableBean`
- Métodos com `@PostConstruct` e `@PreDestroy`

Logue cada etapa e descubra a ordem real de execução.

---

## 6. JPA/Hibernate

### Exercício 6.1 — N+1 problem

Modele `Author` com `List<Book>` em relação `@OneToMany` lazy.

- Liste todos autores e seus livros
- Ative log de SQL e veja as N+1 queries
- Resolva de três formas: `JOIN FETCH`, `@EntityGraph`, e `@BatchSize`
- Compare o SQL gerado

### Exercício 6.2 — Isolation levels

Em duas threads, simule os problemas:

- Lost update
- Dirty read
- Phantom read

Veja como cada isolation level (`READ_COMMITTED`, `REPEATABLE_READ`, `SERIALIZABLE`) afeta o comportamento. Use H2 ou PostgreSQL.

### Exercício 6.3 — Optimistic vs Pessimistic locking

Implemente atualização concorrente em uma entidade `Account` (saldo):

- Com `@Version` (otimista)
- Com `PESSIMISTIC_WRITE`

Compare comportamento sob conflito.

---

## 7. Testes

### Exercício 7.1 — TDD do zero

Implemente um `ShoppingCart` usando TDD estrito (red → green → refactor):

- Adicionar item
- Remover item
- Calcular total com desconto progressivo

Não escreva código de produção sem teste falhando antes.

### Exercício 7.2 — Testcontainers

Substitua um teste de integração que usa H2 por um que sobe PostgreSQL real com Testcontainers.

Veja diferenças de comportamento (ex: queries específicas do Postgres que H2 aceita "errado").

### Exercício 7.3 — Mutation testing

Rode PIT em um projeto com cobertura "alta" (acima de 80%).

Veja o mutation score real. Provavelmente vai cair para 50-60%. Melhore os testes para matar mutantes sobreviventes.

---

## 8. Design e Arquitetura

### Exercício 8.1 — Refactor para SOLID

Pegue uma classe de 300+ linhas (do seu trabalho ou GitHub público) que viola SRP claramente.

Refatore em pequenos passos, com testes garantindo que nada quebra. Documente cada decisão.

### Exercício 8.2 — Hexagonal

Implemente um caso de uso simples (cadastro de usuário com envio de email) seguindo arquitetura hexagonal:

- Domínio puro no centro
- Ports (interfaces) para banco e email
- Adapters para Spring Data e SMTP

Substitua adapters em testes.

### Exercício 8.3 — Idempotência

Implemente um endpoint `POST /orders` que aceita header `Idempotency-Key`.

Garanta que requisições repetidas com a mesma chave não criem orders duplicados, mesmo sob concorrência.

Pense em todos os casos:
- Requisição em andamento
- Completa com sucesso
- Falha

---

## Como Tirar Máximo Proveito

Resolva um por dia ou um por semana, dependendo do tempo disponível.

Para cada exercício:

1. **Implemente** — sem IA, sem copiar
2. **Escreva testes** — cubra os casos principais
3. **Documente o aprendizado** — escreva 2-3 parágrafos no seu próprio repositório explicando:
    - O que aprendeu
    - Trade-offs envolvidos
    - Quando aplicaria isso em produção

Esse hábito de articular o aprendizado é o que solidifica conhecimento de sênior.

### Sugestão de ordem

1. **Lambdas e Streams** (seções 1 e 2) — para soltar a mão
2. **Concorrência** (seção 3) — o que mais separa pleno de sênior
3. **Demais tópicos** — alterne conforme interesse e necessidade do trabalho

---

## Checklist de progresso

- [ ] 1.1 — Pipeline de streams com nomes
- [ ] 1.2 — Validator customizado com chain
- [ ] 1.3 — Function composition (andThen vs compose)
- [ ] 1.4 — Method references
- [ ] 2.1 — Collectors em Orders
- [ ] 2.2 — Collector customizado
- [ ] 2.3 — Streams vs loops
- [ ] 3.1 — Race condition e soluções
- [ ] 3.2 — Producer-Consumer
- [ ] 3.3 — CompletableFuture com timeout
- [ ] 3.4 — Virtual Threads vs Platform Threads
- [ ] 3.5 — Deadlock e resolução
- [ ] 4.1 — Memory leak e heap dump
- [ ] 4.2 — JMH benchmark
- [ ] 4.3 — GC tuning
- [ ] 5.1 — IoC do zero
- [ ] 5.2 — Self-invocation pitfall
- [ ] 5.3 — Bean lifecycle
- [ ] 6.1 — N+1 problem
- [ ] 6.2 — Isolation levels
- [ ] 6.3 — Optimistic vs Pessimistic locking
- [ ] 7.1 — TDD do zero
- [ ] 7.2 — Testcontainers
- [ ] 7.3 — Mutation testing
- [ ] 8.1 — Refactor para SOLID
- [ ] 8.2 — Arquitetura Hexagonal
- [ ] 8.3 — Idempotência
