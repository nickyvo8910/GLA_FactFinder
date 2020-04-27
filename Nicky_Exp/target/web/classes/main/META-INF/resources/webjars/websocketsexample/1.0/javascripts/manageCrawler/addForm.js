Vue.component("add-form", {
    props: ['id'],
    template:
    '   <div class="modal fade" tabindex="-1" role="dialog" :id="id">' +
    '       <div class="modal-dialog modal-lg" role="document">' +
    '           <div class="modal-content">' +
    '               <div class="modal-header">' +
    '                   <h5 class="modal-title">Add crawler</h5>' +
    '                   <button type="button" class="close" data-dismiss="modal" aria-label="Close">' +
    '                       <span aria-hidden="true">&times;</span>' +
    '                   </button>' +
    '               </div>' +
    '               <div class="modal-body">' +
    '                   <div class="row attribute-row" >' +
    '                       <label class="col-sm-4">sourceName</label>' +
    '                           <div class="col-sm-8">' +
    '                           <input class="w-100" type="text" v-model="mSourceName">' +
    '                       </div>' +
    '                   </div>' +
    '                   <div class="row attribute-row" >' +
    '                       <label class="col-sm-4">collectionName</label>' +
    '                           <div class="col-sm-8">' +
    '                           <input class="w-100" type="text" v-model="mCollectionName">' +
    '                       </div>' +
    '                   </div>' +
    '                   <div class="row attribute-row" >' +
    '                       <label class="col-sm-4">rawCollectionName</label>' +
    '                           <div class="col-sm-8">' +
    '                           <input class="w-100" type="text" v-model="mRawCollectionName">' +
    '                       </div>' +
    '                   </div>' +
    '                   <div class="row attribute-row" >' +
    '                       <label class="col-sm-4">enabled</label>' +
    '                       <div class="col-sm-8">' +
    '                           <input class="w-100" type="checkbox" id="checkbox" v-model="mEnabled">' +
    '                       </div>' +
    '                   </div>' +
    '                   <div class="row attribute-row" >' +
    '                       <label class="col-sm-4">crawlingPeriod</label>' +
    '                       <div class="col-sm-8">' +
    '                           <input type="text" v-model="mCrawlingN">' +
    '                           <select v-model="mTimeUnit">' +
    '                               <option value="sec">sec</option>' +
    '                               <option value="min">min</option>' +
    '                               <option value="hour">hour</option>' +
    '                           </select>' +
    '                       </div>' +
    '                   </div>' +
    '                   <div class="row attribute-row" >' +
    '                       <label class="col-sm-4">authType</label>' +
    '                       <div class="col-sm-8">' +
    '                           <select v-model="mAuthType">' +
    '                               <option value="oauth">oauth</option>' +
    '                               <option value="APIKey">APIKey</option>' +
    '                           </select>' +
    '                       </div>' +
    '                   </div>' +
    '                   <div class="row attribute-row">' +
    '                       <label class="col-sm-4">authInfo</label><div class="col-sm-8"></div>' +
    '                   </div>' +
    '                   <div v-show="mAuthType === oauth">' +
    '                       <div class="row attribute-row">' +
    '                           <label class="col-sm-4">- consumerKey</label>' +
    '                           <div class="col-sm-8"><input class="w-100" type="text" v-model="mConsumerKey"></div>' +
    '                       </div>' +
    '                       <div class="row attribute-row">' +
    '                           <label class="col-sm-4">- consumerSecret</label>' +
    '                           <div class="col-sm-8"><input class="w-100" type="text" v-model="mConsumerSecret"></div>' +
    '                       </div>' +
    '                       <div class="row attribute-row">' +
    '                           <label class="col-sm-4">- accessToken</label>' +
    '                           <div class="col-sm-8"><input class="w-100" type="text" v-model="mAccessToken"></div>' +
    '                       </div>' +
    '                       <div class="row attribute-row">' +
    '                           <label class="col-sm-4">- accessTokenSecret</label>' +
    '                           <div class="col-sm-8"><input class="w-100" type="text" v-model="mAccessTokenSecret"></div>' +
    '                       </div>' +
    '                   </div>' +
    '                   <div v-show="mAuthType === apiKey">' +
    '                       <div class="row attribute-row">' +
    '                           <label class="col-sm-4">- apiKey</label>' +
    '                           <div class="col-sm-8"><input class="w-100" type="text" v-model="mApiKey"></div>' +
    '                       </div>' +
    '                   </div>' +
    '                   <div class="row attribute-row" >' +
    '                       <label class="col-sm-4">apiFormat</label>' +
    '                           <div class="col-sm-8">' +
    '                           <input type="text" class="w-100" v-model="mApiFormat">' +
    '                       </div>' +
    '                   </div>' +
    '                   <div class="row attribute-row" >' +
    '                       <label class="col-sm-4">recordAccess</label>' +
    '                           <div class="col-sm-8">' +
    '                           <input type="text" class="w-100" v-model="mRecordAccess">' +
    '                       </div>' +
    '                   </div>' +
    '                   <div class="row attribute-row" >' +
    '                       <label class="col-sm-4">recordMapping</label>' +
    '                           <div class="col-sm-8">' +
    '                           <textarea type="text" class="w-100" v-model="mRecordMapping"></textarea>' +
    '                       </div>' +
    '                   </div>' +
    '               </div>' +
    '               <div class="modal-footer">' +
    '                   <button type="button" class="btn btn-primary" v-on:click="addSource()" data-dismiss="modal">Add</button>' +
    '                   <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>' +
    '               </div>' +
    '           </div>' +
    '       </div>' +
    '   </div>',
    created: function() {
        // init default value
        this.mSourceName = "bbc-news";
        this.mCollectionName = "bbcNews";
        this.mRawCollectionName = "bbcNewsRaw";
        this.mEnabled = false;
        this.mCrawlingN = 1;
        this.mTimeUnit = "hour";
        this.mAuthType = "APIKey";
        this.mAuthInfo = {};
        this.mConsumerKey = "";
        this.mConsumerSecret = "";
        this.mAccessToken = "";
        this.mAccessTokenSecret = "";
        this.mApiKey = "ca307ec588174aa5be1d6b8f4a6f69bc";
        this.mApiFormat = "https://newsapi.org/v2/everything?sources=bbc-news&q={query}&apiKey={apiKey}";
        this.mRecordAccess = "articles";
        this.mRecordMapping = "{ \"text\": \"description\", \"title\": \"title\", \"author\": \"author\", \"url\": \"url\", \"createdTimestamp\": \"publishedAt\", \"sourceId\": \"source.id\", \"sourceName\": \"source.name\" }";
    },
    data: function() {
        return {
            mSourceName: '',
            mCollectionName: '',
            mRawCollectionName: '',
            mEnabled: '',
            mCrawlingN: '',
            mTimeUnit: '',
            mAuthType: '',
            mAuthInfo: '',
            mConsumerKey: '',
            mConsumerSecret: '',
            mAccessToken: '',
            mAccessTokenSecret: '',
            mApiKey: '',
            mApiFormat: '',
            mRecordAccess: '',
            mRecordMapping: {},
            oauth: "oauth",
            apiKey: "APIKey"
        }
    },
    computed: {

    },
    methods: {
        addSource: function() {
            switch (this.mAuthType) {
                case "oauth":
                    this.mAuthInfo = {
                        consumerKey: this.mConsumerKey,
                        consumerSecret: this.mConsumerSecret,
                        accessToken: this.mAccessToken,
                        accessTokenSecret: this.mAccessTokenSecret
                    };
                    break;
                case "APIKey":
                    this.mAuthInfo = {
                        apiKey: this.mApiKey
                    };
                    break;
            }
            // call the ajax js routing
            let xhr = new XMLHttpRequest();
            xhr.open('POST', jsRoutes.controllers.ManageCrawlerController.addSource().url);
            xhr.setRequestHeader("Content-Type", "text/json");
            let vm = this;
            xhr.onload = function() {
                if (xhr.status === 200) {
                    // show the updated toasted
                    Vue.toasted.show(vm.mSourceName + " is created!", {
                        theme: "bubble",
                        position: "bottom-center",
                        duration : 2000
                    });
                    // refresh the crawlers
                    vm.$emit('update-crawlers')
                }
                else {
                    console.log('Request failed.  Returned status of ')
                    console.log(xhr)
                }
            };
            xhr.send(JSON.stringify({
                sourceName: this.mSourceName,
                collectionName: this.mCollectionName,
                rawCollectionName: this.mRawCollectionName,
                enabled: this.mEnabled,
                crawlingPeriod: [parseInt(this.mCrawlingN), this.mTimeUnit],
                authType: this.mAuthType,
                authInfo: this.mAuthInfo,
                apiFormat: this.mApiFormat,
                recordAccess: this.mRecordAccess,
                recordMapping: JSON.parse(this.mRecordMapping)
            }));
        }
    }
});