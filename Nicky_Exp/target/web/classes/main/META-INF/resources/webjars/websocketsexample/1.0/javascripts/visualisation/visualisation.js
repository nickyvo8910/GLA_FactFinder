Vue.use(Toasted);

let app1 = new Vue({
    el: "#app1",
    created: function() {
        this.getVisualisationData();
    },
    mounted: function() {

    },
    data: {
        query: {},
        visQuery: {
            text: ""
        },
        graphTempData: {
            nodes: [
                {id:"n1", name:"node1", type:"query"},
                {id:"n2", name:"node2", type:"query"},
                {id:"n3", name:"node3", type:"query"},
                {id:"n4", name:"node4", type:"query"},
                {id:"n5", name:"node5", type:"query"},
                {id:"n6", name:"node6", type:"query"},
                {id:"n7", name:"rec1", type:"record"},
                {id:"n8", name:"rec2", type:"record"},
                {id:"n9", name:"rec3", type:"record"},
            ],
            links: [
                {source:"n1", target:"n2", type:"qq"},
                {source:"n2", target:"n3", type:"qq"},
                {source:"n3", target:"n4", type:"qq"},
                {source:"n2", target:"n5", type:"qq"},
                {source:"n4", target:"n6", type:"qq"},
                {source:"n7", target:"n1", type:"rq"},
                {source:"n8", target:"n1", type:"rq"},
                {source:"n9", target:"n1", type:"rq"},
            ]
        },
        graphData: {},
        queries: [],
        lineWidth: 3,
        qqLinkWidth: 5,
        rqLinkWidth: 1,
        queryNodeRadius: 20,
        queryLinkDistance: 300,
        recordNodeRadius: 5,
        recordLinkDistance: {min:25, max:150},
        markerSize: {
            width: 5,
            height: 5
        },
        barGraph: {},
        // loading
        loading: true,
        visNodeColor: "blue"
    },
    computed: {

    },
    methods: {
        updateQuery: function(query) {
            this.query = query;
        },
        getVisualisationData: function() {
            let url = window.location.href;
            let urlSplits = url.split("/");
            let queryDbId = urlSplits[urlSplits.length - 1];
            let xhr = new XMLHttpRequest();
            xhr.open('GET', jsRoutes.controllers.VisualisationController.getVisualisationData(queryDbId).url);
            let vm = this;
            xhr.onload = function() {
                if (xhr.status === 200) {
                    let response = JSON.parse(xhr.response);
                    console.log(response.data);
                    vm.graphData = response.data;
                    vm.visQuery = response.data.query;
                    vm.startBarGraph();
                    vm.startTimelineGraph();
                    vm.loading = false;
                }
                else {
                    console.log('Request failed.  Returned status of ');
                    console.log(xhr);
                }
            };
            xhr.send();
        },
        startBarGraph: function() {
            let graphs = this.graphData.barGraph.graphs;
            graphs = graphs.map(function(graph) {
                if (graph.id === "total") {
                    graph["lineThickness"] = 2;
                    graph["useDataSetColors"] = false;
                    graph["hidden"] = true;
                    graph["visibleInLegend"] = false;
                } else {
                    graph["type"] = "column";
                    graph["fillAlphas"] = 0.9;
                    graph["useDataSetColors"] = false;
                    graph["periodValue"] = "Sum";
                    graph["balloonText"] = "Number of " + graph.id + " records: <b>[[value]]</b>";
                }
                return graph
            });
            this.barGraph = AmCharts.makeChart("bar-graph", {
                "type": "stock",
                "theme": "light",
                "categoryAxesSettings": {
                    "minPeriod": "mm"
                },
                "dataSets": [{
                    "fieldMappings": this.graphData.barGraph.dataSets.fieldMappings,
                    "dataProvider": this.graphData.barGraph.dataProvider,
                    "categoryField": "date"
                }],
                "panels": [ {
                    "showCategoryAxis": true,
                    "title": "Records",
                    "percentHeight": 100,
                    "stockGraphs": graphs,
                    "stockLegend": {
                    }
                }],
                "chartScrollbarSettings": {
                    "graph": "total",
                    "usePeriod": "10mm",
                    "position": "bottom"
                },
                "chartCursorSettings": {
                    "valueBalloonsEnabled": true
                },
                "periodSelector": {
                    "position": "top",
                    "dateFormat": "YYYY-MM-DD JJ:NN",
                    "inputFieldWidth": 150,
                    "periods": [ {
                        "period": "hh",
                        "count": 1,
                        "label": "1 hour"
                    }, {
                        "period": "hh",
                        "count": 2,
                        "label": "2 hours"
                    }, {
                        "period": "hh",
                        "count": 5,
                        "label": "5 hour"
                    }, {
                        "period": "hh",
                        "count": 12,
                        "label": "12 hours"
                    }, {
                        "period": "MAX",
                        "label": "MAX"
                    } ]
                },
                "panelsSettings": {
                    "usePrefixes": true
                },
                "export": {
                    "enabled": true,
                    "position": "bottom-right"
                }
            } );
        },
        startTimelineGraph: function() {
            let vm = this;

            let svg = d3.select("#timeline-graph"),
                width = +svg.attr("width"),
                height = +svg.attr("height");

            let linkForce = d3.forceLink(this.graphData.timelineGraph.links)
                .id(function(d) { return d.id; })
                .distance(linkDistance)
                .strength(1);

            svg.append("svg:defs").append("svg:marker")
                .attr("id", "triangleQuery")
                .attr("viewBox", "0 -5 10 10")
                .attr("refX", 17)
                .attr("refY", 0)
                .attr("markerWidth", this.markerSize.width)
                .attr("markerHeight", this.markerSize.height)
                .attr("orient", "auto")
                .attr("xoverflow", "visible")
                .append("path")
                .attr("d", "M0,-5L10,0L0,5")
                .style("fill", "black");

            svg.append("svg:defs").append("svg:marker")
                .attr("id", "triangleRecord")
                .attr("viewBox", "0 -5 10 10")
                .attr("refX", 0)
                .attr("refY", 0)
                .attr("markerWidth", this.markerSize.width)
                .attr("markerHeight", this.markerSize.height)
                .attr("orient", "auto")
                .attr("xoverflow", "visible")
                .append("path")
                .attr("d", "M0,-5L10,0L0,5")
                .style("fill", "grey");

            let g = svg.append("g")
                .attr("class", "everything");

            let link = g.append("g")
                .attr("class", "links")
                .selectAll("line")
                .data(this.graphData.timelineGraph.links)
                .enter().append("line")
                .attr("stroke-width", linkWidth)
                .attr('marker-end', arrow)
                .style('stroke', linkColor);

            let tooltip = d3.select("#tool-tip")
                // .attr("class", "tooltip")
                .style("opacity", 0)
                // fix to right-top corner
                .style("position", "absolute")
                .style("top", "0px")
                .style("right", "0px");

            let prevClicked = {};

            let node = g.append("g")
                .attr("class", "nodes")
                .selectAll("circle")
                .data(this.graphData.timelineGraph.nodes)
                .enter()
                .append("circle")
                .attr("r", radius)
                .attr("fill", nodeColor)
                .on("click", function(d) {
                    if (!isObjectEmpty(prevClicked)) {
                        // de-highlight the previously clicked elements
                        prevClicked.style("stroke", "#fff");
                        prevClicked.style("stroke-width", "1.5px");
                        if (isSameObject(prevClicked, d3.select(this))) {
                            // hide tooltip
                            tooltip.transition()
                                .duration(200)
                                .style("opacity", 0);
                            return;
                        }
                    }
                    prevClicked = d3.select(this);
                    // highlight the element
                    d3.select(this).style("stroke", "yellow");
                    d3.select(this).style("stroke-width", "5px");

                    tooltip.transition()
                        .duration(200)
                        .style("opacity", 0.9);
                    let htmlText = getCardText(d);
                    tooltip.html(htmlText)
                });

            function isSameObject(a, b) {
                return JSON.stringify(a) === JSON.stringify(b);
            }

            function isObjectEmpty(obj) {
                return Object.keys(obj).length === 0 && obj.constructor === Object;
            }

            function getCardText(d) {
                let htmlText = '';
                if (d.type === 'query') {
                    let dateString =  moment.unix(d.createdTimestamp / 1000).format('Do MMMM YYYY, h:mm:ss a');
                    let relativeTime = moment.unix(d.createdTimestamp / 1000).fromNow();
                    htmlText = '<div class="card-body">' +
                        '<h4 class="card-title">Query Information</h4>' +
                        '<p class="card-text"><b>Text: </b>' + d.name + '</p>' +
                        '<p class="card-text"><b>Created at: </b>' + dateString + ' (' + relativeTime + ')' + '</p>' +
                        '<p class="card-text"><b>All related records: </b>' + d.numRelatedRecords + '</p>' +
                        '<p class="card-text"><b>Enabled sources: </b>' + JSON.stringify(d.enabledSources) + '</p>' +
                        '<p class="card-text"><b>Previous queries: </b>' + JSON.stringify(d.prevQueries) + '</p>' +
                        '<p class="card-text"><b>Expanded queries: </b>' + JSON.stringify(d.expandedQueries) + '</p>' +
                        '<p class="card-text"><b>DbId: </b>' + d.id + '</p>' +
                        '</div>';
                }
                else {
                    htmlText += '<div class="card-body">' +
                        '<h4 class="card-title">Record Information</h4>';
                    if (d.title === "")
                        htmlText += '<p class="card-text"><b>Title: </b>N/A</p>';
                    else
                        htmlText += '<p class="card-text"><b>Title: </b>' + d.title + '</p>';
                    let dateString =  moment.unix(d.createdTimestamp / 1000).format('Do MMMM YYYY, h:mm:ss a');
                    let relativeTime = moment.unix(d.createdTimestamp / 1000).fromNow();
                    htmlText +=
                        '<p class="card-text"><b>Url: </b><a href="' + d.url + '" target="_blank" rel="noopener noreferrer">' + d.url + '</a></p>' +
                        '<p class="card-text"><b>Text: </b>' + d.name + '</p>' +
                        '<p class="card-text"><b>Created at: </b>' + dateString + ' (' + relativeTime + ')' + '</p>' +
                        '<p class="card-text"><b>Source: </b>' + d.source + '</p>' +
                        '<p class="card-text"><b>DbId: </b>' + d.id + '</p>' +
                        '</div>';
                }
                return htmlText
            }
            var x = 1;

            // simulation setup
            let simulation = d3.forceSimulation().nodes(this.graphData.timelineGraph.nodes);
            simulation
                .force("charge_force", d3.forceManyBody().strength(-5))
                // .force("forceY", d3.forceY().strength(0.1))
                .force("center-force", d3.forceCenter(width/2, height/2))
                .force("links", linkForce)
                .force("collide", d3.forceCollide().strength(1).iterations(1).radius(5));
            simulation.on("tick", tickActions);

            let dragHandler = d3.drag()
                .on("start", dragStart)
                .on("drag", dragDrag)
                .on("end", dragEnd);
            dragHandler(node);

            let zoom_handler = d3.zoom()
                .on("zoom", zoom_actions);
            zoom_handler(svg);
            svg.on("dblclick.zoom", null);

            function zoom_actions(){
                g.attr("transform", d3.event.transform)
            }

            function arrow(d) {
                if (d.type === 'qq') {
                    return 'url(#triangleQuery)';
                } else {
                    return 'url(#triangleRecord)';
                }
            }

            function linkColor(d) {
                if (d.type === 'qq') {
                    return "black"
                } else {
                    return "grey"
                }
            }

            function linkWidth(d) {
                if (d.type === 'qq') {
                    return vm.qqLinkWidth;
                } else {
                    return vm.rqLinkWidth;
                }
            }

            function linkDistance(d) {
                if (d.type === 'qq') {
                    return vm.queryLinkDistance;
                } else if (d.type === 'rq') {
                    // random between min & max
                    let min = vm.recordLinkDistance.min;
                    let max = vm.recordLinkDistance.max;
                    return Math.random() * (max - min) + min;
                }
            }

            function radius(d) {
                if (d.type === 'query') {
                    return vm.queryNodeRadius;
                } else {
                    return vm.recordNodeRadius;
                }
            }

            function nodeColor(d) {
                if (d.type === 'query') {
                    if (d.id === vm.visQuery.dbId)
                        return vm.visNodeColor;
                    else
                        return 'red';
                } else {
                    return stringToColour(d.source)
                    // return 'grey';
                }
            }

            function stringToColour(str) {
                let hash = 0;
                for (let i = 0; i < str.length; i++) {
                    hash = str.charCodeAt(i) + ((hash << 5) - hash);
                }
                let colour = '#';
                for (let i = 0; i < 3; i++) {
                    let value = (hash >> (i * 8)) & 0xFF;
                    colour += ('00' + value.toString(16)).substr(-2);
                }
                return colour;
            }

            function dragStart(d) {
                if (!d3.event.active) simulation.alphaTarget(0.3).restart();
                d.fx = d.x;
                d.fy = d.y;
            }

            function dragDrag(d) {
                d.fx = d3.event.x;
                d.fy = d3.event.y;
            }

            function dragEnd(d) {
                if (!d3.event.active) simulation.alphaTarget(0);
                d.fx = null;
                d.fy = null;
            }

            function tickActions() {
                //update circle positions each tick of the simulation
                node
                // .attr("cx", function(d) { return d.x; })
                // .attr("cy", function(d) { return d.y; });
                    .attr("transform", function (d) {return "translate(" + d.x + ", " + d.y + ")";});

                //update link positions
                //simply tells one end of the line to follow one node around
                //and the other end of the line to follow the other node around
                link
                    .attr("x1", function(d) { return d.source.x; })
                    .attr("y1", function(d) { return d.source.y; })
                    .attr("x2", function(d) { return d.target.x; })
                    .attr("y2", function(d) { return d.target.y; });

            }
        },
        navigateToIndex: function() {
            window.location.href = jsRoutes.controllers.HomeController.index().url
        },
        navigateToManageCrawler: function() {
            window.location.href = jsRoutes.controllers.ManageCrawlerController.manageCrawler().url
        },
        navigateToTrackQuery: function() {
            window.location.href = jsRoutes.controllers.TrackQueryController.trackQuery().url
        }
    }
});

