Vue.component("crawler-switch", {
    props: ['source'],
    template:
        '<div class="col-sm-6">' +
        '    <div class="row">' +
        '        <div class="col-sm-6 p-0">{{ source.sourceName }}</div>' +
        '        <div class="col-sm-6 p-0">' +
        '            <label class="switch">' +
        '                <input type="checkbox" v-model="source.isEnabledCrawling" >' +
        '                <span class="slider round"></span>' +
        '            </label>' +
        '        </div>' +
        '     </div>' +
        '</div>'
});