




import itertools as itt
from collections import Counter, defaultdict
from typing import Iterable, List, Mapping, Optional, Set, Tuple, TypeVar

from pybel import BELGraph
from pybel.constants import ANNOTATIONS, RELATION
from pybel.dsl import BaseEntity
from pybel.struct.filters.edge_predicates import edge_has_annotation
from pybel.struct.filters.typing import NodePredicate
from pybel.struct.summary import (
    count_annotations, count_pathologies, count_relations, get_annotations, get_unused_annotations,
    get_unused_list_annotation_values, iter_annotation_value_pairs, iter_annotation_values,
)
from .contradictions import pair_has_contradiction

__all__ = [
    'count_relations',
    'get_edge_relations',
    'count_unique_relations',
    'count_annotations',
    'get_annotations',
    'get_annotations_containing_keyword',
    'count_annotation_values',
    'count_annotation_values_filtered',
    'pair_is_consistent',
    'get_consistent_edges',
    'get_contradictory_pairs',
    'count_pathologies',
    'get_unused_annotations',
    'get_unused_list_annotation_values',
]

A = TypeVar('A')
B = TypeVar('B')


def group_dict_set(iterator: Iterable[Tuple[A, B]]) -> Mapping[A, Set[B]]:
    

    d = defaultdict(set)
    for key, value in iterator:
        d[key].add(value)
    return dict(d)


def get_edge_relations(graph: BELGraph) -> Mapping[Tuple[BaseEntity, BaseEntity], Set[str]]:
    

    return group_dict_set(
        ((u, v), d[RELATION])
        for u, v, d in graph.edges(data=True)
    )


def count_unique_relations(graph: BELGraph) -> Counter:
    

    return Counter(itt.chain.from_iterable(get_edge_relations(graph).values()))


def get_annotations_containing_keyword(graph: BELGraph, keyword: str) -> List[Mapping[str, str]]:
    

    return [
        {
            'annotation': annotation,
            'value': value
        }
        for annotation, value in iter_annotation_value_pairs(graph)
        if keyword.lower() in value.lower()
    ]


def count_annotation_values(graph: BELGraph, annotation: str) -> Counter:
    

    return Counter(iter_annotation_values(graph, annotation))


def count_annotation_values_filtered(graph: BELGraph,
                                     annotation: str,
                                     source_predicate: Optional[NodePredicate] = None,
                                     target_predicate: Optional[NodePredicate] = None,
                                     ) -> Counter:
    

    if source_predicate and target_predicate:
        return Counter(
            data[ANNOTATIONS][annotation]
            for u, v, data in graph.edges(data=True)
            if edge_has_annotation(data, annotation) and source_predicate(graph, u) and target_predicate(graph, v)
        )
    elif source_predicate:
        return Counter(
            data[ANNOTATIONS][annotation]
            for u, v, data in graph.edges(data=True)
            if edge_has_annotation(data, annotation) and source_predicate(graph, u)
        )
    elif target_predicate:
        return Counter(
            data[ANNOTATIONS][annotation]
            for u, v, data in graph.edges(data=True)
            if edge_has_annotation(data, annotation) and target_predicate(graph, u)
        )
    else:
        return Counter(
            data[ANNOTATIONS][annotation]
            for u, v, data in graph.edges(data=True)
            if edge_has_annotation(data, annotation)
        )


def pair_is_consistent(graph: BELGraph, u: BaseEntity, v: BaseEntity) -> Optional[str]:
    

    relations = {data[RELATION] for data in graph[u][v].values()}

    if 1 != len(relations):
        return

    return list(relations)[0]


def get_contradictory_pairs(graph: BELGraph) -> Iterable[Tuple[BaseEntity, BaseEntity]]:
    

    for u, v in graph.edges():
        if pair_has_contradiction(graph, u, v):
            yield u, v


def get_consistent_edges(graph: BELGraph) -> Iterable[Tuple[BaseEntity, BaseEntity]]:
    

    for u, v in graph.edges():
        if pair_is_consistent(graph, u, v):
            yield u, v
