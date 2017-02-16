package cn.zy.ef.refresh;

public abstract class RefreshListenerAdapter implements PullListener {
        @Override
        public void onPullingDown(RefreshLayout refreshLayout, float fraction) {
        }

        @Override
        public void onPullingUp(RefreshLayout refreshLayout, float fraction) {
        }

        @Override
        public void onPullDownReleasing(RefreshLayout refreshLayout, float fraction) {
        }

        @Override
        public void onPullUpReleasing(RefreshLayout refreshLayout, float fraction) {
        }

        @Override
        public void onRefresh(RefreshLayout refreshLayout) {
        }

        @Override
        public void onLoadMore(RefreshLayout refreshLayout) {
        }

        @Override
        public void onFinishRefresh() {

        }

        @Override
        public void onFinishLoadMore() {

        }

        @Override
        public void onRefreshCanceled() {

        }

        @Override
        public void onLoadmoreCanceled() {

        }
}