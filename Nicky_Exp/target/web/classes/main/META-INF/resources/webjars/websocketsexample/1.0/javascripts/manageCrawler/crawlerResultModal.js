Vue.component("crawler-result-modal", {
    props: {
        source: Object,
        id: String
    },
    data: function() {
        return {
            crawlerStats: [],
            loading: true,
        }
    },
    template:
        '<b-modal :id="id" title="Crawl result" :ok-only="true">' +
        '   <spinner :loading="loading"></spinner>' +
        '   <b-table hover :items="statOnly"></b-table>' +
        '</b-modal>',
    computed: {
        statOnly: function() {
            return this.crawlerStats.map(stat => {
                return {
                    "query": stat.queryText,
                    "#newRecords": stat.numberOfNewRecords,
                    "#totalRecords": stat.numberOfTotalRecords
                }
            });
        }
    },
    methods: {
        forceCrawl: function() {
            // clear  body content
            this.crawlerStats = [];
            // show loading
            this.loading = true;

            let xhr = new XMLHttpRequest();
            xhr.open('GET', jsRoutes.controllers.ManageCrawlerController.forceCrawl(this.source.sourceName).url);
            let vm = this;
            xhr.onload = function() {
                if (xhr.status === 200) {
                    let response = JSON.parse(xhr.response);
                    vm.crawlerStats = response.data;
                    // refresh the crawlers
                    vm.$emit('update-crawlers');
                    vm.loading = false;
                }
                else {
                    console.log('Request failed.  Returned status of ');
                    console.log(xhr);
                }
            };
            xhr.send();
        }
    }
});